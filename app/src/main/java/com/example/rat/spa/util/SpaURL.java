package com.example.rat.spa.util;

public class SpaURL {
  private static final String SERVER_ENTRY = "http://103.199.18.108:8081/api/";
  public static final String USER_INDEX = SERVER_ENTRY + "user/index";
  public static final String USER_LOGIN = SERVER_ENTRY + "account/login";
  public static final String GET_ADDRESS = SERVER_ENTRY + "account/getaddress";
  public static final String USER_UPDATE = SERVER_ENTRY + "user/update";
  public static final String STORE_INDEX = SERVER_ENTRY + "store/index";
  public static final String STORE_DETAIL = SERVER_ENTRY + "store/detail";
  public static final String STORE_RATE = SERVER_ENTRY + "store/rate";
  public static final String ORDER_CREATE = SERVER_ENTRY + "order/create";
  public static final String ORDER_INDEX = SERVER_ENTRY + "order/index";
  public static final String ACCOUNT_LOGOUT = SERVER_ENTRY + "account/Logout";
  // lấy danh sách cửa hàng được sắp xếp theo vị trí người dùng
  public static final String ORDER_GETSTORE = SERVER_ENTRY + "account/GetStoreToOrder";
  // khi click vào cửa hàng thì gửi lên IDStore: id để lấy danh sách dịch vụ của cửa hàng đó
  public static final String ORDER_GETSERVICE = SERVER_ENTRY + "manager/services";
  // xét quyền isCancel, nếu bằng true thì hiện buton hủy lên, ấn vào thì call vào api này
  public static final String ORDER_CANCEL = SERVER_ENTRY + "order/Cancel";

}