package vn.phatbee.tuan6_sqllite.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vn.phatbee.tuan6_sqllite.Model.NotesModel;
import vn.phatbee.tuan6_sqllite.R;

public class NotesAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<NotesModel> noteList;

    // Tạo ViewHolder
    private class ViewHolder
    {
        TextView textViewNote;
        ImageView imageViewEdit;
        ImageView imageViewDelete;
    }

    // Tạo Constructor
    public NotesAdapter(Context context, int layout, List<NotesModel> noteList) {
        this.context = context;
        this.layout = layout;
        this.noteList = noteList;
    }

    @Override
    public int getCount() {
        return noteList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Gọi ViewHolder
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            viewHolder.textViewNote = (TextView) convertView.findViewById(R.id.textViewNameNote);
            viewHolder.imageViewDelete = (ImageView) convertView.findViewById(R.id.imageViewDelete);
            viewHolder.imageViewEdit = (ImageView) convertView.findViewById(R.id.imageViewEdit);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Lấy giá trị
        NotesModel notes = noteList.get(position);
        viewHolder.textViewNote.setText(notes.getNameNote());
        viewHolder.imageViewEdit.setImageResource(R.drawable.edit);
        viewHolder.imageViewDelete.setImageResource(R.drawable.delete);

        return convertView;
    }
}
