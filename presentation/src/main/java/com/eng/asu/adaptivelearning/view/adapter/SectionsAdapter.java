package com.eng.asu.adaptivelearning.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import androidx.databinding.DataBindingUtil;

import com.adaptivelearning.server.FancyModel.FancyLecture;
import com.adaptivelearning.server.FancyModel.FancyQuiz;
import com.adaptivelearning.server.FancyModel.FancySection;
import com.eng.asu.adaptivelearning.R;
import com.eng.asu.adaptivelearning.databinding.ItemviewLectureBinding;
import com.eng.asu.adaptivelearning.databinding.ItemviewSectionBinding;

import java.util.List;

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
        FancySection fancySection = sections.get(groupPosition);
        return fancySection.getFancyLectures().size() + (fancySection.getFancyQuiz() != null ? 1 : 0);
    }

    @Override
    public FancySection getGroup(int groupPosition) {
        return sections.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        FancySection section = sections.get(groupPosition);
        return childPosition == section.getFancyLectures().size() ? section.getFancyQuiz() : section.getFancyLectures().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return sections.get(groupPosition).getSectionId();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        FancySection section = sections.get(groupPosition);
        return childPosition == section.getFancyLectures().size() ? section.getFancyQuiz().getQuizId() : section.getFancyLectures().get(childPosition).getLectureId();
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

        FancySection section = sections.get(groupPosition);
        if (childPosition == section.getFancyLectures().size()) {
            bindLecture(null, childPosition, groupPosition, binding, section.getFancyQuiz());
        } else {
            FancyLecture lecture = section.getFancyLectures().get(childPosition);
            bindLecture(lecture, childPosition, groupPosition, binding, null);
        }

        return binding.getRoot();
    }

    private void bindSection(ItemviewSectionBinding sectionBinding, int groupPosition, boolean isExpanded) {
        sectionBinding.title.setText(sections.get(groupPosition).getTitle());
        sectionBinding.arrow.setSelected(isExpanded);
    }

    @SuppressLint("SetTextI18n")
    private void bindLecture(FancyLecture lecture, int childPosition, int parentPosition, ItemviewLectureBinding binding, FancyQuiz quiz) {
        int lectureNumber = childPosition + 1;

        for (int i = 0; i < parentPosition; i++) {
            FancySection section = sections.get(i);
            lectureNumber += section.getFancyLectures().size() + (section.getFancyQuiz() != null ? 1 : 0);
        }

        binding.position.setText(String.valueOf(lectureNumber));

        if (quiz != null) {
            binding.type.setImageResource(R.drawable.ic_quiz);
            binding.button.setText("Take Quiz");
            binding.title.setText(quiz.getTitle());
            binding.button.setOnClickListener(v -> listener.onQuizClicked(quiz));
        } else {
            binding.title.setText(lecture.getName());
            if (lecture.isFile()) {
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
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public interface OnLectureClickedListener {
        void onLectureClicked(FancyLecture lecture);

        void onQuizClicked(FancyQuiz quiz);
    }
}
