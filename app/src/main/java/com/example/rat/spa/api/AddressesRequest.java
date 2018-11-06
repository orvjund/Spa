package com.example.rat.spa.api;

import android.content.Context;

import com.android.volley.VolleyError;
import com.example.rat.spa.model.District;
import com.example.rat.spa.model.Province;
import com.example.rat.spa.util.SpaURL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public abstract class AddressesRequest extends RequestBase {
  public AddressesRequest(Context context) {
    super(context);
    request();
  }

  @Override
  public void handleResult(String result) {
    try {
      ArrayList<Province> provinces = new ArrayList<>();
      ArrayList<District> districts = new ArrayList<>();

      JSONObject data = new JSONObject(result)
          .getJSONObject("Data");

      JSONArray provincesJSON = data.getJSONArray("Provinces");
      for (int i = 0; i < provincesJSON.length(); i++) {
        int id = provincesJSON.getJSONObject(i).getInt("ID");
        String code = provincesJSON.getJSONObject(i).getString("Code");
        String name = provincesJSON.getJSONObject(i).getString("Name");
        provinces.add(new Province(id, code, name));
      }

      JSONArray districtsJSON = data.getJSONArray("Districts");
      for (int i = 0; i < districtsJSON.length(); i++) {
        int id = districtsJSON.getJSONObject(i).getInt("ID");
        String name = districtsJSON.getJSONObject(i).getString("Name");

        String provinceCode = districtsJSON.getJSONObject(i).getString("ProvinceCode");
        Province province = findProvinceByCode(provinceCode, provinces);
        province.addDistrict(new District(id, name));
      }
      handleAddresses(provinces);
    } catch (JSONException e) {
      handleError(new VolleyError(e));
    }
  }

  public abstract void handleAddresses(ArrayList<Province> provinces);

  Province findProvinceByCode(String provinceCode, ArrayList<Province> provinces) {
    for (int i = 0; i < provinces.size(); i++) {
      if (provinces.get(i).getCode().equals(provinceCode)) {
        return provinces.get(i);
      }
    }
    return null;
  }

  private void request() {
    GET(SpaURL.GET_ADDRESS, null);
  }
}
