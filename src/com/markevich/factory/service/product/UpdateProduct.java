package com.markevich.factory.service.product;

import biznesObgectFactory.Product;
import com.markevich.factory.Connect;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

public class UpdateProduct {

    protected UpdateProduct() {
    }

    public void updateProduct(Product product) {
        Connect connect = new Connect();
        JSONWriter jsonWriter = connect.getJsonWriter();
        jsonWriter.object();
        buildHeadersSection(jsonWriter);
        buildParameters(jsonWriter, product);
        jsonWriter.endObject();
        connect.flush();
        JSONTokener jsonTokener = connect.getJsonTokener();
        JSONObject jsonObject = (JSONObject) jsonTokener.nextValue();
        JSONObject jsonObjectHeader = jsonObject.getJSONObject("headers");
        Integer statusCode = jsonObjectHeader.getInt("status-code");
        String statusMessage = jsonObjectHeader.getString("status-message");
        System.out.println("Status code: " + statusCode + "\nStatus massage: " + statusMessage);
        connect.closeStream();
    }

    private void buildHeadersSection(JSONWriter jsonWriter) {
        jsonWriter.key("headers");
        jsonWriter.object();
        jsonWriter.key("command-name").value("update-product");
        jsonWriter.endObject();
    }

    private void buildParameters(JSONWriter jsonWriter, Product product) {
        jsonWriter.key("parameters");
        jsonWriter.object();
        jsonWriter.key("id").value(product.getId());
        jsonWriter.key("material").value(product.getMaterial());
        jsonWriter.key("size-box").value(product.getSizeBox());
        jsonWriter.key("order-id").value(product.getOrderId());
        jsonWriter.key("color").value(product.getColor());
        jsonWriter.key("image").value(product.getImage());
        jsonWriter.key("texture").value(product.getTexture());
        jsonWriter.endObject();
    }
}
