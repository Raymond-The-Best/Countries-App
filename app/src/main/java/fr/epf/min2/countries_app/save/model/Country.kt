package fr.epf.min2.countries_app.save.model
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import com.google.gson.annotations.SerializedName

@Parcelize
data class Country(
    @SerializedName("name") val name: Name,
    @SerializedName("tld") val tld: List<String>,
    @SerializedName("independent") val independent: Boolean,
    @SerializedName("unMember") val unMember: Boolean,
    @SerializedName("currencies") val currencies: Map<String, Currency>,
    @SerializedName("capital") val capital: List<String>,
    @SerializedName("altSpellings") val altSpellings: List<String>,
    @SerializedName("region") val region: String,
    @SerializedName("subregion") val subregion: String,
    @SerializedName("languages") val languages: Map<String, String>,
    @SerializedName("translations") val translations: Map<String, Translation>,
    @SerializedName("demonyms") val demonyms: Map<String, Demonym>,
    @SerializedName("maps") val maps: Map<String, String>,
    @SerializedName("population") val population: Long,
    @SerializedName("car") val car: Car,
    @SerializedName("continents") val continents: List<String>,
    @SerializedName("flags") val flags: Flags,
    @SerializedName("capitalInfo") val capitalInfo: CapitalInfo
) : Parcelable{

    companion object {
        fun generate(size: Int = 20) =
            (1..size).map {
                Country(
                    Name("Country $it", "Country $it", mapOf("fra" to NativeName("Pays $it", "Pays $it"))),
                    listOf(".fr"),
                    true,
                    true,
                    mapOf("EUR" to Currency("Euro", "€")),
                    listOf("Paris"),
                    listOf("France"),
                    "Europe",
                    "Western Europe",
                    mapOf("fra" to "Français"),
                    mapOf("fra" to Translation("Pays $it", "Pays $it")),
                    mapOf("fra" to Demonym("Française", "Français")),
                    mapOf("fra" to "https://goo.gl/maps/g7QxxSFsWyTPKuzd7"),
                    1000000,
                    Car("right"),
                    listOf("Europe"),
                    Flags("https://flagcdn.com/w320/fr.png"),
                    CapitalInfo(listOf(48.8566, 2.3522))
                )
            }
    }
}

@Parcelize
data class Name(
    @SerializedName("common") var common: String,
    @SerializedName("official") var official: String,
    @SerializedName("nativeName") val nativeName: Map<String, NativeName>
) : Parcelable

@Parcelize
data class NativeName(
    @SerializedName("official") val official: String,
    @SerializedName("common") val common: String
) : Parcelable

@Parcelize
data class Currency(
    @SerializedName("name") val name: String,
    @SerializedName("symbol") val symbol: String
) : Parcelable

@Parcelize
data class Demonym(
    @SerializedName("f") val female: String,
    @SerializedName("m") val male: String
) : Parcelable

@Parcelize
data class Car(
    @SerializedName("side") val side: String
) : Parcelable

@Parcelize
data class Flags(
    @SerializedName("png") val png: String
) : Parcelable

@Parcelize
data class CapitalInfo(
    @SerializedName("latlng") val latlng: List<Double>
) : Parcelable

@Parcelize
data class Translation(
    @SerializedName("official") val official: String,
    @SerializedName("common") val common: String
) : Parcelable


fun List<Country>.toCountryString(): String {
    return this.joinToString(separator = ", ") { country ->
        country.name.common
    }
}