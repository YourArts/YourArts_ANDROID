package com.yg.yourexhibit.Tabs;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yg.yourexhibit.App.ApplicationController;
import com.yg.yourexhibit.R;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitCollectionDetailResult;
import com.yg.yourexhibit.Util.EventBus;
import com.yg.yourexhibit.Util.NetworkController;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    private NetworkController networkController;
    private ExhibitCollectionDetailResult detailResult;
    final int REQ_CODE_SELECT_IMAGE = 100;
    private Uri data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_collection_edit, container, false);
        ButterKnife.bind(this, v);
        EventBus.getInstance().register(this);
        networkController = new NetworkController();
        initFragment();
        //TODO : 여기에 원래 Shared에 저장한 아이디 들어가야 함
        //networkController.getCollectionData(ApplicationController.getInstance().token);

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

//                    BitmapFactory.Options options = new BitmapFactory.Options();
//
//                    InputStream in = null; // here, you need to get your context.
//                    try {
//                        in = getContentResolver().openInputStream(this.data);
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
//
//                    Bitmap bitmap = BitmapFactory.decodeStream(in, null, options); // InputStream 으로부터 Bitmap 을 만들어 준다.
//                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos); // 압축 옵션( JPEG, PNG ) , 품질 설정 ( 0 - 100까지의 int형 ),


                    Glide.with(this)
                            .load(data.getData())
                            .into(editImg);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
