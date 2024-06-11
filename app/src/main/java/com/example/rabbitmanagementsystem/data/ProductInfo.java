package com.example.rabbitmanagementsystem.data;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductInfo implements Parcelable {
    private String productId;
    private String productName;
    private String productCategory;
    private String productPrice;
    private String productDescription;
    private String imageUrl;

    public ProductInfo() {
        // Default constructor required for Firebase
    }

    public ProductInfo(String productId, String productName, String productCategory, String productPrice, String productDescription, String imageUrl) {
        this.productId = productId;
        this.productName = productName;
        this.productCategory = productCategory;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.imageUrl = imageUrl;
    }

    protected ProductInfo(Parcel in) {
        productId = in.readString();
        productName = in.readString();
        productCategory = in.readString();
        productPrice = in.readString();
        productDescription = in.readString();
        imageUrl = in.readString();
    }

    public static final Creator<ProductInfo> CREATOR = new Creator<ProductInfo>() {
        @Override
        public ProductInfo createFromParcel(Parcel in) {
            return new ProductInfo(in);
        }

        @Override
        public ProductInfo[] newArray(int size) {
            return new ProductInfo[size];
        }
    };

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productId);
        dest.writeString(productName);
        dest.writeString(productCategory);
        dest.writeString(productPrice);
        dest.writeString(productDescription);
        dest.writeString(imageUrl);
    }
}
