package common.barter.com.barterapp.SubCategory;

import common.barter.com.barterapp.ModelCallBackListener;

/**
 * Created by vikram on 18/05/16.
 */
public class SubCategoryPresenter implements ModelCallBackListener {

    SubCategoryFragment subCategoryFragment;
    SubCategoryModel subCategoryModel;

    public SubCategoryPresenter() {
    }
    public void initializeAndGetHomeModel(){
        subCategoryModel = new SubCategoryModel();
    }

    public void setSubCategoryFragment(SubCategoryFragment subCategoryFragment) {
        this.subCategoryFragment = subCategoryFragment;
    }

    public void setSubCategoryModel(SubCategoryModel subCategoryModel) {
        this.subCategoryModel = subCategoryModel;
    }

    @Override
    public void onSuccess(Object o) {

    }

    @Override
    public void onFailure(String errorMessage) {

    }

    public void navigateToViewPosts(String subCategory) {
        subCategoryFragment.navigateToViewPosts(subCategory);
    }

    public void navigateToPostAd() {
        subCategoryFragment.navigateToPostAd();
    }
}
