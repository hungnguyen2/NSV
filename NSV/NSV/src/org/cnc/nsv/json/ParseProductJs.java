package org.cnc.nsv.json;

import java.util.ArrayList;
import java.util.List;

import org.cnc.nsv.product.OjProduct;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseProductJs {
	String TAG = ParseProductJs.class.getSimpleName();

	public List<OjProduct> parse(String data) throws JSONException {
		List<OjProduct> products = new ArrayList<OjProduct>();
		if (data != null) {
			JSONObject root = new JSONObject(data);
			JSONArray item = root.getJSONArray("items");
			for (int i = 0; i < item.length(); i++) {
				JSONObject product = item.getJSONObject(i);
				OjProduct ojProduct = new OjProduct();
				ojProduct.setId(product.getString("id"));
				ojProduct.setName(product.getString("name"));
				ojProduct.setUrl(product.getString("url"));
				String thumbUrl = product.getString("image_thumb");
				String imageUrl = product.getString("image");
				ojProduct.setThumbUrl(thumbUrl);
				ojProduct.setImageUrl(imageUrl);
				JSONObject property = product.getJSONObject("property");
				ojProduct.setQuy_cach(property.getString("quy_cach"));
				ojProduct.setLoai_hang(property.getString("loai_hang"));
				ojProduct.setTieu_chuan(property.getString("tieu_chuan"));
				ojProduct.setDuong_kinh(property.getString("duong_kinh"));
				ojProduct.setGioi_han_chay(property.getString("gioi_han_chay"));
				ojProduct.setGioi_han_nut(property.getString("gioi_han_nut"));
				ojProduct.setDo_dan_dai(property.getString("do_dan_dai"));
				ojProduct.setThu_uong_nguoi(property.getString("thu_uon_nguoi"));
				ojProduct.setSoLuongThanh(property.getString("so_luong_thanh"));
				ojProduct.setTrongLuong(property.getString("trong_luong"));
				ojProduct.setSaiLech(property.getString("sai_lech"));
				
				products.add(ojProduct);
			}
		}
		return products;

	}

}
