package fr.epf.min2.countries_app.save.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Playlist(
    @SerializedName("nom") val nom : String,
    @SerializedName("dateCreation") val dateCreation : Date,
    @SerializedName("pays") val pays : MutableSet<Country>,
    @SerializedName("isDefault") val isDefault: Boolean,
    @SerializedName("isDeletable") val isDeletable: Boolean,
    @SerializedName("isEditable") val isEditable: Boolean,
    @SerializedName("image") val image: String
) : Parcelable