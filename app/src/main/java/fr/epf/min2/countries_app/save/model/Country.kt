package fr.epf.min2.countries_app.save.model
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class Country(val name: String,
                   val capital: String,
                   val region: String,
                   val subregion: String,
                   val altSpellings: ArrayList<String>,
                   val population: Long,
                   val latlng: ArrayList<Double>,
                   val area: Double,
                   val timezones: ArrayList<String>,
                   val currencies: ArrayList<Currency>,
                   val languages: ArrayList<Language>,
                   val flag: String) : Parcelable {
    companion object {
        fun generate(size: Int = 20) =
            (1..size).map {
                Country("Country $it",
                    "Capital $it",
                    "Region $it",
                    "Subregion $it",
                    arrayListOf("AltSpellings $it"),
                    it.toLong(),
                    arrayListOf(0.0, 0.0),
                    it.toDouble(),
                    arrayListOf("Timezones $it"),
                    arrayListOf(Currency("Code $it", "Name $it", "Symbol $it")),
                    arrayListOf(Language("Name $it", "NativeName $it")),
                    "Flag $it")
            }
    }
}
@Parcelize
data class Currency (
    val code: String,
    val name: String,
    val symbol: String
): Parcelable {}
@Parcelize
data class Language (
    val name: String,
    val nativeName: String
): Parcelable {}
