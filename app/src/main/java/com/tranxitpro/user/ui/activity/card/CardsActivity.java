package com.tranxitpro.user.ui.activity.card;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tranxitpro.user.R;
import com.tranxitpro.user.base.BaseActivity;
import com.tranxitpro.user.data.network.model.Card;
import com.tranxitpro.user.ui.activity.add_card.AddCardActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.tranxitpro.user.common.Constants.RIDE_REQUEST.PAYMENT_MODE;

public class CardsActivity extends BaseActivity implements CardsIView {

    List<Card> cardsList = new ArrayList<>();
    @BindView(R.id.cards_rv)
    RecyclerView cardsRv;
    @BindView(R.id.add_card)
    Button addCard;
    private CardsPresenter<CardsActivity> presenter = new CardsPresenter<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_cards;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        presenter.attachView(this);
        // Activity title will be updated after the locale has changed in Runtime
        setTitle(getString(R.string.select_card));

        CardAdapter adapter = new CardAdapter(cardsList);
        cardsRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        cardsRv.setAdapter(adapter);

    }

    @OnClick({R.id.paypal})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.paypal:
                Intent intent = new Intent();
                intent.putExtra(PAYMENT_MODE, "PAYPAL");
                setResult(Activity.RESULT_OK, intent);
                finish();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.card();
    }

    @Override
    public void onSuccess(List<Card> cards) {
        cardsList.clear();
        cardsList.addAll(cards);
        cardsRv.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onError(Throwable e) {
        handleError(e);
    }

    @OnClick(R.id.add_card)
    public void onViewClicked() {
        startActivity(new Intent(this, AddCardActivity.class));
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    private class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyViewHolder> {

        private List<Card> list;

        CardAdapter(List<Card> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_card, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Card item = list.get(position);
            holder.card.setText(getString(R.string.card_ , item.getLastFour()));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private LinearLayout itemView;
            private TextView card;

            MyViewHolder(View view) {
                super(view);
                itemView = view.findViewById(R.id.item_view);
                card = view.findViewById(R.id.card);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                Card card = list.get(position);
                if (view.getId() == R.id.item_view) {
                    Intent intent = new Intent();
                    intent.putExtra("payment_mode", "CARD");
                    intent.putExtra("card_id", card.getCardId());
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }

            }
        }
    }

}
