package com.yg.yourexhibit.Tabs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.otto.Subscribe;
import com.yg.yourexhibit.Adapter.Collection.TabCollectionEditAdapter;
import com.yg.yourexhibit.App.ApplicationController;
import com.yg.yourexhibit.R;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitCollectionDetailResult;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitSearchResponse;
import com.yg.yourexhibit.Util.EventBus;
import com.yg.yourexhibit.Util.EventCode;
import com.yg.yourexhibit.Util.NetworkController;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by 2yg on 2017. 10. 18..
 */

public class Tab_Collection_Edit extends Fragment{

    @BindView(R.id.collection_edit_img)
    ImageView editImg;

    @BindView(R.id.collection_edit_search)
    EditText search;

    @BindView(R.id.collection_edit_content)
    EditText context;

    @BindView(R.id.collection_edit_text)
    TextView text;

    @BindView(R.id.collection_edit_save)
    TextView save;

    @BindView(R.id.collection_edit_search_result)
    RecyclerView result;

    @BindView(R.id.collection_edit_imgframe)
    RelativeLayout frame;

    @BindView(R.id.collection_detail_container2)
    RelativeLayout frame2;

    @BindView(R.id.collection_edit_search_text)
    TextView searchText;

    private ExhibitCollectionDetailResult detailResult;
    final int REQ_CODE_SELECT_IMAGE = 100;
    private Uri data;
    private MultipartBody.Part profile_pic;

    private TabCollectionEditAdapter tabCollectionEditAdapter;
    private LinearLayoutManager linearLayoutManager;
    private NetworkController networkController;
    private ArrayList<ExhibitSearchResponse> searchList;
    private int idx;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_collection_edit, container, false);
        ButterKnife.bind(this, v);
        EventBus.getInstance().register(this);
        networkController = new NetworkController();
        initFragment();
        //getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
       // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //TODO : 여기에 원래 Shared에 저장한 아이디 들어가야 함
        //networkController.getCollectionData(ApplicationController.getInstance().token);
       // getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(search.getText().length()!=0) {
                    result.setVisibility(View.VISIBLE);
                    frame.setVisibility(View.GONE);
                    frame2.setVisibility(View.GONE);
                    networkController.getCollectionSearchData(search.getText().toString());
                }else{
                    result.setVisibility(View.GONE);
                    frame.setVisibility(View.VISIBLE);
                    frame2.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return v;
    }

    public void initFragment(){
        detailResult = ApplicationController.getInstance().getExhibitCollectionDetailResult();
        if(ApplicationController.getInstance().isFromDetail()){
            //디테일에서 옴
            text.setVisibility(View.GONE);
            Glide.with(this).load(detailResult.getCollection_image()).into(editImg);
            context.setText(detailResult.getCollection_content());
        }else{
            //콜렉션 메인에서 홈
        }
    }

//    @Subscribe
//    public void onEventLoad(Integer code){
//        switch (code){
//            case EventCode.EVENT_CODE_COLLECTION_EDIT1:
//                initFragment1();
//                break;
//            case EventCode.EVENT_CODE_COLLECTION_EDIT2:
//                initFragment2();
//                break;
//        }
//    }
    public void initFragment1(){

    }

    public void initFragment2(){
        text.setVisibility(View.GONE);
        detailResult = ApplicationController.getInstance().getExhibitCollectionDetailResult();
        Glide.with(this).load(detailResult.getCollection_image()).into(editImg);
        //search.setText();
        context.setText(detailResult.getCollection_content());
    }

    @OnClick(R.id.collection_edit_save)
    public void saveEdit(){
        if(ApplicationController.getInstance().isFromDetail()){
            //디테일로부터 옴->얜 수정 해야 함
            networkController.putCollection(ApplicationController.getInstance().token, context.getText().toString(),
                    ApplicationController.getInstance().getCollectionIdx());

        }else{
            RequestBody colIdx = RequestBody.create(MediaType.parse("text/pain"), String.valueOf(idx));
            RequestBody name = RequestBody.create(MediaType.parse("text/pain"), searchText.getText().toString());
            RequestBody content = RequestBody.create(MediaType.parse("text/pain"), context.getText().toString());

            //returnFrag();


            networkController.postCollection(ApplicationController.getInstance().token, colIdx, content, profile_pic);
        }



        //RequestBody teamname = RequestBody.create(MediaType.parse("text/pain"), make_teamname.toString());
        //RequestBody region = RequestBody.create(MediaType.parse("text/pain"), make_region.toString());
        //RequestBody manager = RequestBody.create(MediaType.parse("text/pain"), make_manager.toString());
        //RequestBody found_at = RequestBody.create(MediaType.parse("text/pain"), make_found.toString());

        //networkController.postCollection(ApplicationController.getInstance().token, );

    }

    @OnClick(R.id.collection_edit_pic)
    public void changeImage(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQ_CODE_SELECT_IMAGE) {
            if (resultCode == getActivity().RESULT_OK) {
                try {
                    this.data = data.getData();

                    BitmapFactory.Options options = new BitmapFactory.Options();

                    InputStream in = null; // here, you need to get your context.
                    try {
                        in = getActivity().getContentResolver().openInputStream(this.data);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Bitmap bitmap = BitmapFactory.decodeStream(in, null, options); // InputStream 으로부터 Bitmap 을 만들어 준다.
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
                    RequestBody photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray());
                    File photo = new File(this.data.toString()); // 가져온 파일의 이름을 알아내려고 사용합니다

                    ///RequestBody photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray());
                    // MultipartBody.Part 실제 파일의 이름을 보내기 위해 사용!!
                    profile_pic = MultipartBody.Part.createFormData("collection_image", photo.getName(), photoBody);
                    //body = MultipartBody.Part.createFormData("image", photo.getName(), profile_pic);

                    Glide.with(this)
                            .load(data.getData())
                            .into(editImg);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public View.OnClickListener clickEvent = new View.OnClickListener() {
        public void onClick(View v) {
            int itemPosition = result.getChildPosition(v);
            idx = ApplicationController.getInstance().getExhibitSearchResult().get(itemPosition).getExhibition_idx();
            search.setText("");
            search.clearFocus();
            frame.setVisibility(View.VISIBLE);
            frame2.setVisibility(View.VISIBLE);
            result.setVisibility(View.GONE);
            searchText.setText(ApplicationController.getInstance().getExhibitSearchResult().get(itemPosition).getExhibition_name());
            //networkController.getDetailData(0, idx);
        }
    };
    public void setResultList(){
        searchList = ApplicationController.getInstance().getExhibitSearchResult();
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        result.setLayoutManager(linearLayoutManager);

        tabCollectionEditAdapter = new TabCollectionEditAdapter(searchList, clickEvent);
        result.setAdapter(tabCollectionEditAdapter);
    }

    @Subscribe
    public void onEventLoad(Integer code){
        switch (code){
            case EventCode.EVENT_CODE_COLLECTION_SEARCH:
                setResultList();
                break;
            case EventCode.EVENT_CODE_COLLECTION_POST:
                returnFrag();
                break;
        }
    }

    public void returnFrag(){
        if(ApplicationController.getInstance().isFromDetail()){
            Fragment fromFrag = null, toFrag = null;
            fromFrag = getActivity().getSupportFragmentManager().findFragmentByTag("edit");
            toFrag = getActivity().getSupportFragmentManager().findFragmentByTag("detail");
            final android.support.v4.app.FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.detach(fromFrag);
            //ft.attach(toFrag);
            ft.commit();
        }else{
            //그냥 콜렉션으로부 옴
            Fragment fromFrag = null, toFrag = null;
            fromFrag = getActivity().getSupportFragmentManager().findFragmentByTag("edit");
            toFrag = getActivity().getSupportFragmentManager().findFragmentByTag("base");
            final android.support.v4.app.FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.detach(fromFrag);
            ft.attach(toFrag);
            ft.commit();
        }
    }

    @OnClick(R.id.collection_edit_content)
    public void clickContent(){

    }
}
