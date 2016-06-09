package com.vincentnock.lgt_compagnon.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vincentnock.lgt_compagnon.R;
import com.vincentnock.lgt_compagnon.models.Party;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by vincent on 08/06/2016.
 */
@EViewGroup(R.layout.view_party_item)
public class PartyItemView extends RelativeLayout {

    @ViewById
    TextView tvParty;

    public PartyItemView(Context context) {
        super(context);
    }

    public PartyItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PartyItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void bind(Party party) {
        tvParty.setText(party.getCreatedAtFormatted());
    }
}
