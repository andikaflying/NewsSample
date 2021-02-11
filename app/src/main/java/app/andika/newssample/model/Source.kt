package app.andika.newssample.model

import android.os.Parcel
import android.os.Parcelable

data class Source(
    val id: Any,
    val name: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        TODO("id"),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Source> {
        override fun createFromParcel(parcel: Parcel): Source {
            return Source(parcel)
        }

        override fun newArray(size: Int): Array<Source?> {
            return arrayOfNulls(size)
        }
    }
}