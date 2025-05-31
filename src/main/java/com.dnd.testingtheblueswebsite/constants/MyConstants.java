package com.dnd.testingtheblueswebsite.constants;

import com.dnd.testingtheblueswebsite.utils.MyUtils;

public class MyConstants {
    public static final String BASE_URL = "https://theblues.com.vn";
    public static final String username = "samuelclarkvn";
    public static final String password = "duynguyen123";
    public static final String email = "samuelclarkvn@gmail.com";
    public static final String testUsername = "testuser";
    public static final String testPassword = "testpassword123";
    public static final String specialEmail = "samuelclarkvn@@gmail.com";
    public static final String loginSuccessMessage = "Xin chào samuelclarkvn";
    public static final String loginFailureMessage = "Tài khoản không đúng, vui lòng kiểm tra và thử lại. Hoặc gửi qua email support@matbao.ws để được giúp đỡ.";
    public static final String longUsername = "samuelclarkvn12345678901234567890123456789012345678901234567890123456789012345678901234567890abcdefghijklmnopqrstuvwxyz";
    public static final String longPassword = "abc12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
    public static final String unTrimmedPassword = password + "   ";
    public static final String accountDetailsUpdatedMessage = "Thông tin tài khoản đã được cập nhật.";

    public static final String validRegisterEmail = MyUtils.randomEmail();
    public static final String alreadyRegisteredEmail = email;
    public static final String invalidFormatEmail = "invalid-email";
    public static final String unTrimmedEmail = "   " + validRegisterEmail + "   ";
    public static final String longEmail = MyUtils.randomLongString() + "@gmail.com";
    public static final String tooLongEmailErrMsg = "Lỗi: Tên người dùng không thể dài hơn 60 kí tự.";
    public static final String emailAlreadyRegisteredErrMsg = "Lỗi: An account is already registered with your email address. Please log in.";
    public static final String registerWithEmptyEmailErrMsg = "Lỗi: Vui lòng cung cấp địa chỉ email hợp lệ.";





}
