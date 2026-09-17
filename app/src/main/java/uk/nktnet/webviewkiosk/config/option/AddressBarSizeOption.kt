package uk.nktnet.webviewkiosk.config.option

import androidx.annotation.StringRes
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.TextUnit
import uk.nktnet.webviewkiosk.R

enum class AddressBarSizeOption(
    val label: String,
    @StringRes val labelRes: Int,
    val heightDp: Dp,
    val paddingDp: Dp,
    val fontSizeSp: TextUnit,
    val moreVertHeightDp: Dp,
    val searchIconSizeDp: Dp,
    val searchIconPaddingDp: Dp,
) {
    EXTRA_SMALL(
        label = "Extra Small",
        labelRes = R.string.address_bar_size_option_extra_small,
        heightDp = 35.dp,
        paddingDp = 4.dp,
        fontSizeSp = 12.sp,
        moreVertHeightDp = 30.dp,
        searchIconSizeDp = 25.dp,
        searchIconPaddingDp = 3.dp,
    ),
    SMALL(
        label = "Small",
        labelRes = R.string.address_bar_size_option_small,
        heightDp = 45.dp,
        paddingDp = 6.dp,
        fontSizeSp = 14.sp,
        moreVertHeightDp = 38.dp,
        searchIconSizeDp = 35.dp,
        searchIconPaddingDp = 6.dp,
    ),
    MEDIUM(
        label = "Medium",
        labelRes = R.string.address_bar_size_option_medium,
        heightDp = 55.dp,
        paddingDp = 8.dp,
        fontSizeSp = 16.sp,
        moreVertHeightDp = 44.dp,
        searchIconSizeDp = 40.dp,
        searchIconPaddingDp = 8.dp,
    ),
    LARGE(
        label = "Large",
        labelRes = R.string.address_bar_size_option_large,
        heightDp = 65.dp,
        paddingDp = 10.dp,
        fontSizeSp = 20.sp,
        moreVertHeightDp = 52.dp,
        searchIconSizeDp = 45.dp,
        searchIconPaddingDp = 10.dp,
    ),
    EXTRA_LARGE(
        label = "Extra Large",
        labelRes = R.string.address_bar_size_option_extra_large,
        heightDp = 75.dp,
        paddingDp = 12.dp,
        fontSizeSp = 26.sp,
        moreVertHeightDp = 64.dp,
        searchIconSizeDp = 56.dp,
        searchIconPaddingDp = 12.dp,
    );

    companion object {
        fun fromString(value: String?): AddressBarSizeOption {
            return entries.find {
                it.name.equals(value, true)
                || it.label.equals(value, true)
            } ?: MEDIUM
        }
    }
}
