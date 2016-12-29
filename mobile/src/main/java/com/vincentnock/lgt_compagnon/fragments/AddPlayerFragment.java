package com.vincentnock.lgt_compagnon.fragments;

import android.graphics.Bitmap;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.github.oliveiradev.lib.RxPhoto;
import com.github.oliveiradev.lib.shared.TypeRequest;
import com.squareup.picasso.Picasso;
import com.vincentnock.lgt_compagnon.LGTApplication;
import com.vincentnock.lgt_compagnon.R;
import com.vincentnock.lgt_compagnon.models.Player;
import com.vincentnock.lgt_compagnon.models.events.PlayerEvent;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import io.realm.Realm;

/**
 * Created by vincent on 25/12/2016.
 */
@EFragment(R.layout.fragment_add_player)
public class AddPlayerFragment extends DialogFragment implements Toolbar.OnMenuItemClickListener {

    @ViewById
    ImageView ivPhoto;

    @ViewById
    EditText etName, etPhone;

    @ViewById
    Toolbar toolbar;

    Player player;

    @AfterViews
    void init() {
        player = new Player();
        player.uuid = UUID.randomUUID().toString();

        toolbar.inflateMenu(R.menu.menu_add_player);
        toolbar.setOnMenuItemClickListener(this);
    }

    @Click
    void btnAddPhoto() {
        RxPhoto.requestBitmap(getContext(), TypeRequest.CAMERA)
                .map(bitmap -> {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 70, bos);
                    byte[] bitmapdata = bos.toByteArray();
                    return new ByteArrayInputStream(bitmapdata);
                })
                .subscribe(byteArrayInputStream -> {
                    uploadPhoto(byteArrayInputStream);
                });
    }

    @Background
    public void uploadPhoto(InputStream inputStream) {
        try {
            Cloudinary cloudinary = ((LGTApplication) getActivity().getApplication()).getCloudinary();
            cloudinary.uploader().upload(inputStream, ObjectUtils.asMap("public_id", player.uuid));
            String url = cloudinary.url().generate(player.uuid);
            updatePlayerWithPhotoPath(url);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @UiThread
    public void updatePlayerWithPhotoPath(String path) {
        Picasso.with(getContext()).load(path).into(ivPhoto);
        player.imagePath = path;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.menuValid) {
            player.name = etName.getText().toString();
            player.phoneNumber = etPhone.getText().toString();

            Realm.getDefaultInstance().executeTransaction(realm -> {
                realm.copyToRealm(player);
            });

            EventBus.getDefault().post(new PlayerEvent(player));
            dismiss();
        }
        return true;
    }
}
