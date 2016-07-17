package common.barter.com.barterapp.postad;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import common.barter.com.barterapp.CommonResources;
import common.barter.com.barterapp.LocationsDialog;
import common.barter.com.barterapp.MessagesString;
import common.barter.com.barterapp.globalhome.GlobalHome;

/**
 * Created by vikramb on 16/07/16.
 */
public class PostAdListener {
    private PostAdAPresenter presenter;

    public PostAdListener(PostAdAPresenter presenter) {
        this.presenter = presenter;
    }

    public View.OnClickListener getPhotoButtonOnClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onCameraIconClick();

            }
        };
    }

    public View.OnClickListener getOnClickPhotoListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.selectOrClickPhoto(MessagesString.CAMERA_REQUEST);

            }
        };
    }

    public  View.OnClickListener getOnClickPickFromGalleryListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.selectOrClickPhoto(MessagesString.GALLERY_REQUEST);

            }
        };
    }

    public View.OnClickListener getSelectCityOnClickListener (){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onSelectCityClicked();
            }
        };
    }

    public AdapterView.OnItemClickListener getOnLocationSelectListener (){
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.onLoctionSelected(parent,position);
            }
        };
    }

    public AdapterView.OnItemClickListener getRemovePhotoListener (){
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.onRemovePhotoClick(position);

            }
        };
    }

    public View.OnClickListener getOnLocalitySelectListener (){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onLocalitySelectClicked();
            }
        };
    }
    public AdapterView.OnItemClickListener getOnLocalitySelection (){
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.onLocalitySelection(parent,position);
            }
        };
    }
    public View.OnClickListener getOnPostAdClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onPostAdClicked();
            }
        };
    }

    public View.OnClickListener getOnSubCategorySelectListener (){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onSubCategorySelected();
            }
        };
    }

    public AdapterView.OnItemClickListener getSubCategorySelectionListener (){
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.onSubCategoryItemSelected(parent,position);
            }
        };
    }

    public View.OnClickListener getOnCategorySelectListener (){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onCategorySelected();
            }
        };
    }

    public AdapterView.OnItemClickListener getCategorySelectionListener (){
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.onCategoryItemSelected(parent,position);
            }
        };
    }

}
