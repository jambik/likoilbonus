package com.likoil.likoilbonus.mvp.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.likoil.likoilbonus.R;
import com.likoil.likoilbonus.mvp.network.DiscountData;
import com.likoil.likoilbonus.mvp.presentation.presenter.HistoryPresenter;
import com.likoil.likoilbonus.mvp.presentation.view.HistoryView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryFragment extends MvpAppCompatFragment implements HistoryView {
    public static final String TAG = "HistoryFragment";
    @InjectPresenter
    HistoryPresenter mHistoryPresenter;

    @BindView(R.id.recycleView)
    RecyclerView recycleView;


    @BindView(R.id.progressBar)
    View progressBar;

    private DiscountData discountData;

    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        ButterKnife.bind(this, view);

        recycleView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        return view;
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setData(DiscountData data) {
        discountData = data;
        MyAdapter myAdapter = new MyAdapter();
        recycleView.setAdapter(myAdapter);
    }

    @Override
    public void showError(String error) {

    }

    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm");

    public class DiscountViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtDateStation)
        TextView txtDateStation;
        @BindView(R.id.txtInfo)
        TextView txtInfo;
        @BindView(R.id.txtBonus)
        TextView txtBonus;

        void setItem(DiscountData.DiscountItem item) {
            txtDateStation.setText(String.format("%s - %s", simpleDateFormat.format(item.getDate()), item.getFuel_name()));
            txtInfo.setText(String.format("%s лит. - %s руб.", item.getVolume(), item.getAmount()));
            txtBonus.setText(String.format("Бонус - %s", item.getPoint()));
        }

        DiscountViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class MyAdapter extends RecyclerView.Adapter<DiscountViewHolder> {
        @Override
        public DiscountViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new DiscountViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.discount_item, parent, false));
        }

        @Override
        public void onBindViewHolder(DiscountViewHolder holder, int position) {
            holder.setItem(discountData.getDiscounts().get(position));
        }

        @Override
        public int getItemCount() {
            return discountData.getDiscounts().size();
        }
    }
}
