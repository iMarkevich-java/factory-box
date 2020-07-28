package com.markevich.factory.service.supplier;

import businessObjectFactoryBox.Supplier;
import com.markevich.factory.Connect;
import com.markevich.factory.StatusMessage;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

public class LoadSupplierByID {
    private final String command = "get-supplier-by-id";

    public LoadSupplierByID() {
    }

    public Supplier loadSupplierByID(String id) {
        Connect connect = new Connect();
        JSONWriter jsonWriter = connect.getJsonWriter();
        jsonWriter.object();
        buildHeadersSection(jsonWriter);
        buildParameters(jsonWriter, id);
        jsonWriter.endObject();
        connect.flush();
        JSONTokener jsonTokener = connect.getJsonTokener();
        JSONObject jsonObject = (JSONObject) jsonTokener.nextValue();
        Supplier supplier = new Supplier();
        JSONObject jsonObjectHeader = jsonObject.getJSONObject("headers");
        int statusCode = jsonObjectHeader.getInt("status-code");
        StatusMessage.setStatusMessage(command + " : " + jsonObjectHeader.getString("status-message"), statusCode);
        JSONArray jsonArray = jsonObject.getJSONArray("response-data");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            supplier.setCompanyName(object.getString("company-name"));
            supplier.setLegalData(object.getString("legal-data"));
            supplier.setAddress(object.getString("address"));
            supplier.setManager(object.getString("manager"));
            supplier.setId(object.getString("id"));
        }
        connect.closeStream();
        return supplier;
    }

    private void buildHeadersSection(JSONWriter jsonWriter) {
        jsonWriter.key("headers");
        jsonWriter.object();
        jsonWriter.key("command-name").value(command);
        jsonWriter.endObject();
    }

    private void buildParameters(JSONWriter jsonWriter, String id) {
        jsonWriter.key("parameters");
        jsonWriter.object();
        jsonWriter.key("id").value(id);
        jsonWriter.endObject();
    }
}


