package net.xorrizon.bonuscards2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import net.xorrizon.bonuscards2.Card;
import net.xorrizon.bonuscards2.R;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;


public class CardAdapter extends BaseAdapter {

	private List<Card> cardList;
	private SortedSet<Integer> checkedItems = new TreeSet<Integer>();
	private Context context;
	private OnCheckedChangeListener onCheckedChangeListener;
	private OnItemClickListener onItemClickListener;

	private static class ViewHolder {
		private CheckBox checkbox;
		private TextView text_cardName;
		private TextView text_cardOwner;
	}

	public CardAdapter(Context context, List<Card> cardList) {
		super();
		this.context = context;
		this.cardList = cardList;
	}

	@Override
	public int getCount() {
		return cardList.size();
	}

	@Override
	public Card getItem(int position) {
		return cardList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Card card = getItem(position);
		View view = convertView;
		ViewHolder holder;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.card_list_item, parent, false);
			holder = new ViewHolder();
			holder.checkbox = (CheckBox) view.findViewById(R.id.checkbox);
			holder.text_cardName = (TextView) view.findViewById(R.id.card_name);
			holder.text_cardOwner = (TextView) view.findViewById(R.id.card_owner);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		holder.checkbox.setFocusable(false);
		view.setClickable(true);
		view.setFocusable(true);

		holder.text_cardName.setText(card.getName());
		holder.text_cardOwner.setText(card.getOwner());


		holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				checkedItems.remove(position);
				if (isChecked) {
					checkedItems.add(position);
				}
				notifyDataSetChanged();
				if (onCheckedChangeListener != null) {
					onCheckedChangeListener.onCheckedChange();
				}
			}
		});

		if(onItemClickListener != null) {
			view.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					onItemClickListener.onItemClicked(position);
				}
			});
		}

		if (checkedItems.contains(position)) {
			holder.checkbox.setChecked(true);
		} else {
			holder.checkbox.setChecked(false);
		}

		return view;
	}

	public void clearCheckedItems() {
		checkedItems.clear();
	}

	public SortedSet<Integer> getCheckedItemsPositions(){
		return checkedItems;
	}

	public List<Card> getCheckedItems() {
		List<Card> list = new ArrayList<Card>();
		for(int position : checkedItems) {
			list.add(getItem(position));
		}
		return list;
	}

	public int getCheckedItemCount() {
		return  checkedItems.size();
	}

	public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
		this.onCheckedChangeListener = onCheckedChangeListener;
	}

	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}

	public interface OnCheckedChangeListener {
		public void onCheckedChange();
	}

	public interface OnItemClickListener {
		public void onItemClicked(int position);
	}

}
