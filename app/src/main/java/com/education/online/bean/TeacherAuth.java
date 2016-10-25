package com.education.online.bean;

/**
 * Created by Administrator on 2016/10/25.
 */
public class TeacherAuth {

    public TeacherAuth() {
    }

    private String is_id_validate = ""; // 身份证验证0-待审核,1-通过,2-拒绝,3-未提交
    private String is_tc_validate = ""; // 教师证验证0-待审核,1-通过,2-拒绝,3-未提交
    private String is_edu_bg_validate = ""; // 学历验证0-待审核,1-通过,2-拒绝,3-未提交
    private String is_specialty_validate = ""; // 专业资质验证0-待审核,1-通过,2-拒绝,3-未提交
    private String is_unit_validate = ""; // 单位验证0-待审核,1-通过,2-拒绝,3-未提交

    public String getIs_id_validate() {
        return is_id_validate;
    }

    public void setIs_id_validate(String is_id_validate) {
        this.is_id_validate = is_id_validate;
    }

    public String getIs_tc_validate() {
        return is_tc_validate;
    }

    public void setIs_tc_validate(String is_tc_validate) {
        this.is_tc_validate = is_tc_validate;
    }

    public String getIs_edu_bg_validate() {
        return is_edu_bg_validate;
    }

    public void setIs_edu_bg_validate(String is_edu_bg_validate) {
        this.is_edu_bg_validate = is_edu_bg_validate;
    }

    public String getIs_specialty_validate() {
        return is_specialty_validate;
    }

    public void setIs_specialty_validate(String is_specialty_validate) {
        this.is_specialty_validate = is_specialty_validate;
    }

    public String getIs_unit_validate() {
        return is_unit_validate;
    }

    public void setIs_unit_validate(String is_unit_validate) {
        this.is_unit_validate = is_unit_validate;
    }
}
