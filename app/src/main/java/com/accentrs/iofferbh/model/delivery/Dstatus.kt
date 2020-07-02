package com.accentrs.iofferbh.model.delivery

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Dstatus {

    @SerializedName("id")
    @Expose
    private var id: Int? = null

    @SerializedName("option_name")
    @Expose
    private var optionName: String? = null

    @SerializedName("option_value")
    @Expose
    private var optionValue: String? = null

    fun getId(): Int? {
        return id
    }

    fun setId(id: Int?) {
        this.id = id
    }

    fun getOptionName(): String? {
        return optionName
    }

    fun setOptionName(optionName: String?) {
        this.optionName = optionName
    }

    fun getOptionValue(): String? {
        return optionValue
    }

    fun setOptionValue(optionValue: String?) {
        this.optionValue = optionValue
    }


}