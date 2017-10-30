package com.yg.yourexhibit.Tabs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.yg.yourexhibit.Dialog.GalleryDialog;
import com.yg.yourexhibit.R;
import com.yg.yourexhibit.Retrofit.NetworkService;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitCollectionDetailResult;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitSearchResponse;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitWorkResult;
import com.yg.yourexhibit.Retrofit.RetrofitPost.ExhibitCollectionPostResponse;
import com.yg.yourexhibit.Retrofit.RetrofitPut.CollectionPutBody;
import com.yg.yourexhibit.Retrofit.RetrofitPut.ExhibitCollectionPutResponse;
import com.yg.yourexhibit.Util.EventBus;
import com.yg.yourexhibit.Util.EventCode;
import com.yg.yourexhibit.Util.NetworkController;
import com.yg.yourexhibit.Util.SharedPrefrernceController;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 2yg on 2017. 10. 18..
 */

public class Tab_Collection_Edit extends android.support.v4.app.Fragment{

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

    @BindView(R.id.collection_edit_container_frame)
    LinearLayout containerFrame;

    @BindView(R.id.collection_edit_icon)
    ImageView icon;

    private static final String TAG = "LOG::CollectionEdit";

    private GalleryDialog galleryDialog;


    private ExhibitCollectionDetailResult detailResult;
    private ArrayList<ExhibitWorkResult> workResult;

    final int REQ_CODE_SELECT_IMAGE = 100;
    private Uri data;
    private MultipartBody.Part profile_pic;

    private TabCollectionEditAdapter tabCollectionEditAdapter;
    private LinearLayoutManager linearLayoutManager;
    private NetworkController networkController;
    private NetworkService networkService;
    private ArrayList<ExhibitSearchResponse> searchList;
    private int idx = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_collection_edit, container, false);
        ButterKnife.bind(this, v);
        networkService = ApplicationController.getInstance().getNetworkService();
        //EventBus.getInstance().register(this);

        Log.v(TAG, "createView");
        networkController = new NetworkController();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        initFragment();
        ApplicationController.getInstance().setInDetail(false);
        ApplicationController.getInstance().setFromEdit(true);
        //TODO : 여기에 원래 Shared에 저장한 아이디 들어가야 함

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
                    getCollectionSearchData(search.getText().toString());
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
    public void getCollectionSearchData(String search){
        Call<ArrayList<ExhibitSearchResponse>> exhibitSearchResponse = networkService.getSearchResponse(search);
        exhibitSearchResponse.enqueue(new Callback<ArrayList<ExhibitSearchResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<ExhibitSearchResponse>> call, Response<ArrayList<ExhibitSearchResponse>> response) {
                if(response.isSuccessful()){
                    if(response.body()!=null) {
                        ApplicationController.getInstance().setExhibitSearchResult(response.body());
                        //EventBus.getInstance().post(EventCode.EVENT_CODE_COLLECTION_SEARCH);
                        setResultList();
                        Log.v(TAG, "getCollectionSearchSuccess");
                    }else{
                        Log.v(TAG, "getCollectionSearchSuccessButNull");
                    }
                }else{
                    EventBus.getInstance().post(EventCode.EVENT_CODE_NETWORK_FAIL);
                    Log.v(TAG,"getSearchFail");
                }
            }
            @Override
            public void onFailure(Call<ArrayList<ExhibitSearchResponse>> call, Throwable t) {
                Log.v(TAG,"checkNetwork");
            }
        });
    }

    public void initFragment(){
        detailResult = ApplicationController.getInstance().getExhibitCollectionDetailResult();
        workResult = ApplicationController.getInstance().getExhibitWorkResult();

        if(ApplicationController.getInstance().isFromWork()){
            text.setVisibility(View.GONE);
            searchText.setText(ApplicationController.getInstance().getExhibitDetailResult().getExhibition_name());
            Glide.with(this).load(workResult.get(0).getWork_image()).centerCrop().into(editImg);
            idx = ApplicationController.getInstance().getExhibitDetailResult().getExhibition_idx();

            Uri uri = Uri.parse(workResult.get(0).getWork_image());
            URL url = null;
            try {
                url = new URL(workResult.get(0).getWork_image());
                Log.v("이미지", url.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            File photo = new File(uri.getPath());

            BitmapFactory.Options options = new BitmapFactory.Options();
//

            Bitmap bitmap = null;
            try {
                //bitmap = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), uri2);
                bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream(), null, options);
                //BitmapFactory.decodeStream()
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Bitmap bitmap = BitmapFactory.decodeStream(in, null, options); // InputStream 으로부터 Bitmap 을 만들어 준다.
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
            RequestBody photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray());
           // File photo = new File(uri.toString()); // 가져온 파일의 이름을 알아내려고 사용합니다

            ///RequestBody photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray());
            // MultipartBody.Part 실제 파일의 이름을 보내기 위해 사용!!
            profile_pic = MultipartBody.Part.createFormData("collection_image", photo.getName(), photoBody);




        }
        else if(ApplicationController.getInstance().isFromDetail()){
            //디테일에서 옴
            text.setVisibility(View.GONE);
            searchText.setText(detailResult.getExhibition_name());
            Glide.with(this).load(detailResult.getCollection_image()).centerCrop().into(editImg);
            Log.v("이미지",detailResult.getCollection_image());
            context.setText(detailResult.getCollection_content());
        }else{
            //콜렉션 메인에서 옴
        }
    }

    @OnClick(R.id.collection_edit_save)
    public void saveEdit(){
        ApplicationController.getInstance().setFromEdit(true);
        ApplicationController.getInstance().setEditContent(context.getText().toString());
        if(ApplicationController.getInstance().isFromWork()){

            RequestBody colIdx = RequestBody.create(MediaType.parse("text/pain"), String.valueOf(idx));
            RequestBody name = RequestBody.create(MediaType.parse("text/pain"), searchText.getText().toString());
            RequestBody content = RequestBody.create(MediaType.parse("text/pain"), context.getText().toString());



            postCollection(ApplicationController.getInstance().token, colIdx, content, profile_pic);
        } else if(ApplicationController.getInstance().isFromDetail()){
            //디테일로부터 옴->얜 수정 해야 함

            putCollection(ApplicationController.getInstance().token, context.getText().toString(),
                    ApplicationController.getInstance().getCollectionIdx());

        } else{
            RequestBody colIdx = RequestBody.create(MediaType.parse("text/pain"), String.valueOf(idx));
            RequestBody name = RequestBody.create(MediaType.parse("text/pain"), searchText.getText().toString());
            RequestBody content = RequestBody.create(MediaType.parse("text/pain"), context.getText().toString());



            postCollection(ApplicationController.getInstance().token, colIdx, content, profile_pic);
        }
    }

    public void postCollection(String token, RequestBody idx, RequestBody content, MultipartBody.Part image){
        Call<ExhibitCollectionPostResponse> postCollectionResponse = networkService.postCollectionResponse(token, idx, content, image);
        postCollectionResponse.enqueue(new Callback<ExhibitCollectionPostResponse>() {
            @Override
            public void onResponse(Call<ExhibitCollectionPostResponse> call, Response<ExhibitCollectionPostResponse> response) {
                if(response.isSuccessful()){
                    Log.v(TAG, "getCollectionDetailSuccess");
                    if(ApplicationController.getInstance().isFromDetail()){
                        //디테일로부터 옴
                    }else{
                        returnFrag();
                        //그냥 생으로 작성
                        //EventBus.getInstance().post(EventCode.EVENT_CODE_COLLECTION_POST);
                    }
                }else{
                    Log.v(TAG, "getCollectionDetailFail");

                }
            }

            @Override
            public void onFailure(Call<ExhibitCollectionPostResponse> call, Throwable t) {
                Log.v(TAG,"checkNetwork");

            }
        });
    }

    public void putCollection(String token, String content, int idx){
        Call<ExhibitCollectionPutResponse> putCollectionResponse = networkService.putCollectionResponse(token, new CollectionPutBody(content), idx);
        putCollectionResponse.enqueue(new Callback<ExhibitCollectionPutResponse>() {
            @Override
            public void onResponse(Call<ExhibitCollectionPutResponse> call, Response<ExhibitCollectionPutResponse> response) {
                if(response.isSuccessful()){
                    ApplicationController.getInstance().makeToast("수정 완료.");
                    //EventBus.getInstance().post(EventCode.EVENT_CODE_COLLECTION_PUT);
                    returnFrag();
                }else{
                    Log.v(TAG,"putCollectionFail");
                }
            }

            @Override
            public void onFailure(Call<ExhibitCollectionPutResponse> call, Throwable t) {
                Log.v(TAG,"checkNetwork");
            }
        });
    }







    @OnClick(R.id.collection_edit_pic)
    public void changeImage(){
        if(SharedPrefrernceController.getGallery(getActivity())) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
            intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
        }else{
            galleryDialog = new GalleryDialog(getActivity(),
                    "당신의 갤러리에 접근합니다.\n이후 설정에서 변경 가능합니다.",
                    leftListener, // 왼쪽 버튼 이벤트
                    rightListener); // 오른쪽 버튼 이벤트
            galleryDialog.show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQ_CODE_SELECT_IMAGE) {
            if (resultCode == getActivity().RESULT_OK) {
                try {
                    //if(ApplicationController.getInstance().is)
                    this.data = data.getData();
                   Log.v("이미지", this.data.toString());

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

                    text.setVisibility(View.GONE);
                    Glide.with(this)
                            .load(data.getData())
                            .centerCrop()
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
            case EventCode.EVENT_CODE_COLLECTION_PUT:
                returnFrag();
                break;
        }
    }

    public void returnFrag(){
        if(ApplicationController.getInstance().isFromDetail()){
            android.app.Fragment fr = null;
            fr = getActivity().getFragmentManager().findFragmentByTag("toEdit");
           getFragmentManager()
                    .beginTransaction()
                    .disallowAddToBackStack()
                    .replace(R.id.collection_edit_container, new Tab_Collection_Detail())
                    .commit();

//            getActivity()
//                    .getSupportFragmentManager()
//                    .popBackStack();
//
            }else{
            //그냥 콜렉션으로부 옴

            android.support.v4.app.FragmentManager fm = getFragmentManager();

//            for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
//                Log.v(TAG, "count");
//                fm.popBackStack();
//            }

//            Fragment fromFrag = null, toFrag = null;
//            fromFrag = getFragmentManager().findFragmentByTag("base");
//
//            for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
//                Log.v(TAG, "count");
//                fm.popBackStack();
//            }
//
//            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//            ft.attach(fromFrag);
//            ft.commit();
            ApplicationController.getInstance().setFromEdit(true);
//            getFragmentManager()
//                    .beginTransaction()
//                    .disallowAddToBackStack()
//                    .replace(R.id.collection_edit_container, new Tab_Collection())
//                    .commit();

                getActivity().finish();
                startActivity(getActivity().getIntent());

            //여긴 작성했을 때



//            toFrag = getFragmentManager().findFragmentByTag("toBase");
//




        }
    }

    @Override
    public void onResume() {
        Log.v(TAG, "resume");
        super.onResume();

    }

    @OnClick(R.id.collection_edit_content)
    public void clickContent(){

    }

    @Override
    public void onDetach() {
        Log.v(TAG,"detach");

        super.onDetach();
        //EventBus.getInstance().unregister(this);
    }

    @Override
    public void onDestroy() {
        Log.v(TAG,"destroy");
        super.onDestroy();
    }

    @Override
    public void onPause() {
        Log.v(TAG,"pause");
        super.onPause();
    }

    private View.OnClickListener leftListener = new View.OnClickListener() {
        public void onClick(View v) {
            //Toast.makeText(getApplicationContext(), "취소 클릭",
            //Toast.LENGTH_SHORT).show();
            SharedPrefrernceController.setGallery(getActivity(), false);
            galleryDialog.dismiss();

        }
    };

    private View.OnClickListener rightListener = new View.OnClickListener() {
        public void onClick(View v) {
            SharedPrefrernceController.setGallery(getActivity(), true);
            galleryDialog.dismiss();
            changeImage();
        }
    };

    @OnClick(R.id.collection_edit_container_frame)
    public void onClickFrame(){
        searchText.setText(search.getText().toString());
        search.setText("");
        search.clearFocus();
        frame.setVisibility(View.VISIBLE);
        frame2.setVisibility(View.VISIBLE);
        result.setVisibility(View.GONE);
    }

    @OnClick(R.id.collection_edit_icon)
    public void onClickIcon(){
        searchText.setText(search.getText().toString());
        search.setText("");
        search.clearFocus();
        frame.setVisibility(View.VISIBLE);
        frame2.setVisibility(View.VISIBLE);
        result.setVisibility(View.GONE);
    }
}
