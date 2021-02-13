package app.andika.newssample.model

import android.os.Parcel
import android.os.Parcelable

data class AllNewsResponse(
    var articles: List<Article>?,
    val status: String?,
    val totalResults: Int?
) : Parcelable {
    var throwable: Throwable?
        get() = null
        set(value) {
            throwable = value
        }

    constructor(throwable: Throwable)  : this(null, null, null) {
        this.throwable = throwable
    }

    constructor(parcel: Parcel) : this(
        TODO("articles"),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(status)
        parcel.writeInt(totalResults!!)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AllNewsResponse> {
        override fun createFromParcel(parcel: Parcel): AllNewsResponse {
            return AllNewsResponse(parcel)
        }

        override fun newArray(size: Int): Array<AllNewsResponse?> {
            return arrayOfNulls(size)
        }
    }
}