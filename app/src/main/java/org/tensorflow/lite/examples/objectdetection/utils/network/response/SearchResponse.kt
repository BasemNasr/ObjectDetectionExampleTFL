package org.tensorflow.lite.examples.objectdetection.utils.network.response

data class SearchResponse(
    val images: List<Image>? = null,
    val searchParameters: SearchParameters? = null
) {
    data class Image(
        val domain: String? = null,
        val imageHeight: Int? = null,
        val imageUrl: String? = null,
        val imageWidth: Int? = null,
        val link: String? = null,
        val position: Int? = null,
        val source: String? = null,
        val thumbnailHeight: Int? = null,
        val thumbnailUrl: String? = null,
        val thumbnailWidth: Int? = null,
        val title: String? = null
    )

    data class SearchParameters(
        val gl: String? = null,
        val q: String? = null,
        val type: String? = null
    )
}