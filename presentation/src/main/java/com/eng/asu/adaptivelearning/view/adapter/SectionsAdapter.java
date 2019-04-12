package com.eng.asu.adaptivelearning.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.adaptivelearning.server.FancyModel.FancyLecture;
import com.adaptivelearning.server.FancyModel.FancySection;
import com.eng.asu.adaptivelearning.R;
import com.eng.asu.adaptivelearning.databinding.ItemviewLectureBinding;
import com.eng.asu.adaptivelearning.databinding.ItemviewSectionBinding;

import java.util.List;

import androidx.databinding.DataBindingUtil;

public class SectionsAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<FancySection> sections;
    private OnLectureClickedListener listener;

    public SectionsAdapter(Context context, List<FancySection> sections, OnLectureClickedListener listener) {
        this.context = context;
        this.sections = sections;
        this.listener = listener;
    }

    @Override
    public int getGroupCount() {
        return sections.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return sections.get(groupPosition).getFancyLectures().size();
    }

    @Override
    public FancySection getGroup(int groupPosition) {
        return sections.get(groupPosition);
    }

    @Override
    public FancyLecture getChild(int groupPosition, int childPosition) {
        return sections.get(groupPosition).getFancyLectures().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return sections.get(groupPosition).getSectionId();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return sections.get(groupPosition).getFancyLectures().get(childPosition).getLectureId();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ItemviewSectionBinding sectionName = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.itemview_section, parent, false);

        bindSection(sectionName, groupPosition, isExpanded);
        return sectionName.getRoot();
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        ItemviewLectureBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.itemview_lecture, parent, false);

        FancyLecture lecture = sections.get(groupPosition).getFancyLectures().get(childPosition);

        bindLecture(lecture, childPosition, groupPosition, binding);

        return binding.getRoot();
    }

    private void bindSection(ItemviewSectionBinding sectionBinding, int groupPosition, boolean isExpanded) {
        sectionBinding.title.setText(sections.get(groupPosition).getTitle());
        sectionBinding.arrow.setSelected(isExpanded);
    }

    @SuppressLint("SetTextI18n")
    private void bindLecture(FancyLecture lecture, int childPosition, int parentPosition, ItemviewLectureBinding binding) {
        int lectureNumber = childPosition + 1;

        for (int i = 0; i < parentPosition; i++)
            lectureNumber += sections.get(i).getFancyLectures().size();

        binding.position.setText(String.valueOf(lectureNumber));
        binding.title.setText(lecture.getName());

        if (lecture.isQuiz()) {
            binding.type.setImageResource(R.drawable.ic_quiz);
            binding.button.setText("Take Quiz");
        } else if (lecture.isFile()) {
            binding.type.setImageResource(R.drawable.ic_file);
            binding.button.setText("Download");
        } else if (lecture.isVideo()) {
            binding.type.setImageResource(R.drawable.ic_video);
            binding.button.setText("Play");
        } else {
            binding.button.setText("Unknown");
        }
        binding.button.setOnClickListener(v -> listener.onLectureClicked(lecture));
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public interface OnLectureClickedListener {
        void onLectureClicked(FancyLecture lecture);
    }
}
