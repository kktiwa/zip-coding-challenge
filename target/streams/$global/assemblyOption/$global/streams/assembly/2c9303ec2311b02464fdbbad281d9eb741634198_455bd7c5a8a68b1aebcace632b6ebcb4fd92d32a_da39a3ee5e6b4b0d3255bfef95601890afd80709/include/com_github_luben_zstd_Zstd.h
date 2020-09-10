/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_github_luben_zstd_Zstd */

#ifndef _Included_com_github_luben_zstd_Zstd
#define _Included_com_github_luben_zstd_Zstd
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_github_luben_zstd_Zstd
 * Method:    compress
 * Signature: ([B[BI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_luben_zstd_Zstd_compress
  (JNIEnv *, jclass, jbyteArray, jbyteArray, jint);

/*
 * Class:     com_github_luben_zstd_Zstd
 * Method:    compressByteArray
 * Signature: ([BII[BIII)J
 */
JNIEXPORT jlong JNICALL Java_com_github_luben_zstd_Zstd_compressByteArray
  (JNIEnv *, jclass, jbyteArray, jint, jint, jbyteArray, jint, jint, jint);

/*
 * Class:     com_github_luben_zstd_Zstd
 * Method:    compressDirectByteBuffer
 * Signature: (Ljava/nio/ByteBuffer;IILjava/nio/ByteBuffer;III)J
 */
JNIEXPORT jlong JNICALL Java_com_github_luben_zstd_Zstd_compressDirectByteBuffer
  (JNIEnv *, jclass, jobject, jint, jint, jobject, jint, jint, jint);

/*
 * Class:     com_github_luben_zstd_Zstd
 * Method:    compressUnsafe
 * Signature: (JJJJI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_luben_zstd_Zstd_compressUnsafe
  (JNIEnv *, jclass, jlong, jlong, jlong, jlong, jint);

/*
 * Class:     com_github_luben_zstd_Zstd
 * Method:    compressUsingDict
 * Signature: ([BI[BII[BI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_luben_zstd_Zstd_compressUsingDict
  (JNIEnv *, jclass, jbyteArray, jint, jbyteArray, jint, jint, jbyteArray, jint);

/*
 * Class:     com_github_luben_zstd_Zstd
 * Method:    compressDirectByteBufferUsingDict
 * Signature: (Ljava/nio/ByteBuffer;IILjava/nio/ByteBuffer;II[BI)J
 */
JNIEXPORT jlong JNICALL Java_com_github_luben_zstd_Zstd_compressDirectByteBufferUsingDict
  (JNIEnv *, jclass, jobject, jint, jint, jobject, jint, jint, jbyteArray, jint);

/*
 * Class:     com_github_luben_zstd_Zstd
 * Method:    compressFastDict0
 * Signature: ([BI[BIILcom/github/luben/zstd/ZstdDictCompress;)J
 */
JNIEXPORT jlong JNICALL Java_com_github_luben_zstd_Zstd_compressFastDict0
  (JNIEnv *, jclass, jbyteArray, jint, jbyteArray, jint, jint, jobject);

/*
 * Class:     com_github_luben_zstd_Zstd
 * Method:    compressDirectByteBufferFastDict0
 * Signature: (Ljava/nio/ByteBuffer;IILjava/nio/ByteBuffer;IILcom/github/luben/zstd/ZstdDictCompress;)J
 */
JNIEXPORT jlong JNICALL Java_com_github_luben_zstd_Zstd_compressDirectByteBufferFastDict0
  (JNIEnv *, jclass, jobject, jint, jint, jobject, jint, jint, jobject);

/*
 * Class:     com_github_luben_zstd_Zstd
 * Method:    decompress
 * Signature: ([B[B)J
 */
JNIEXPORT jlong JNICALL Java_com_github_luben_zstd_Zstd_decompress
  (JNIEnv *, jclass, jbyteArray, jbyteArray);

/*
 * Class:     com_github_luben_zstd_Zstd
 * Method:    decompressByteArray
 * Signature: ([BII[BII)J
 */
JNIEXPORT jlong JNICALL Java_com_github_luben_zstd_Zstd_decompressByteArray
  (JNIEnv *, jclass, jbyteArray, jint, jint, jbyteArray, jint, jint);

/*
 * Class:     com_github_luben_zstd_Zstd
 * Method:    decompressDirectByteBuffer
 * Signature: (Ljava/nio/ByteBuffer;IILjava/nio/ByteBuffer;II)J
 */
JNIEXPORT jlong JNICALL Java_com_github_luben_zstd_Zstd_decompressDirectByteBuffer
  (JNIEnv *, jclass, jobject, jint, jint, jobject, jint, jint);

/*
 * Class:     com_github_luben_zstd_Zstd
 * Method:    decompressUnsafe
 * Signature: (JJJJ)J
 */
JNIEXPORT jlong JNICALL Java_com_github_luben_zstd_Zstd_decompressUnsafe
  (JNIEnv *, jclass, jlong, jlong, jlong, jlong);

/*
 * Class:     com_github_luben_zstd_Zstd
 * Method:    decompressUsingDict
 * Signature: ([BI[BII[B)J
 */
JNIEXPORT jlong JNICALL Java_com_github_luben_zstd_Zstd_decompressUsingDict
  (JNIEnv *, jclass, jbyteArray, jint, jbyteArray, jint, jint, jbyteArray);

/*
 * Class:     com_github_luben_zstd_Zstd
 * Method:    decompressDirectByteBufferUsingDict
 * Signature: (Ljava/nio/ByteBuffer;IILjava/nio/ByteBuffer;II[B)J
 */
JNIEXPORT jlong JNICALL Java_com_github_luben_zstd_Zstd_decompressDirectByteBufferUsingDict
  (JNIEnv *, jclass, jobject, jint, jint, jobject, jint, jint, jbyteArray);

/*
 * Class:     com_github_luben_zstd_Zstd
 * Method:    decompressFastDict0
 * Signature: ([BI[BIILcom/github/luben/zstd/ZstdDictDecompress;)J
 */
JNIEXPORT jlong JNICALL Java_com_github_luben_zstd_Zstd_decompressFastDict0
  (JNIEnv *, jclass, jbyteArray, jint, jbyteArray, jint, jint, jobject);

/*
 * Class:     com_github_luben_zstd_Zstd
 * Method:    decompressDirectByteBufferFastDict0
 * Signature: (Ljava/nio/ByteBuffer;IILjava/nio/ByteBuffer;IILcom/github/luben/zstd/ZstdDictDecompress;)J
 */
JNIEXPORT jlong JNICALL Java_com_github_luben_zstd_Zstd_decompressDirectByteBufferFastDict0
  (JNIEnv *, jclass, jobject, jint, jint, jobject, jint, jint, jobject);

/*
 * Class:     com_github_luben_zstd_Zstd
 * Method:    decompressedSize
 * Signature: ([B)J
 */
JNIEXPORT jlong JNICALL Java_com_github_luben_zstd_Zstd_decompressedSize
  (JNIEnv *, jclass, jbyteArray);

/*
 * Class:     com_github_luben_zstd_Zstd
 * Method:    decompressedDirectByteBufferSize
 * Signature: (Ljava/nio/ByteBuffer;II)J
 */
JNIEXPORT jlong JNICALL Java_com_github_luben_zstd_Zstd_decompressedDirectByteBufferSize
  (JNIEnv *, jclass, jobject, jint, jint);

/*
 * Class:     com_github_luben_zstd_Zstd
 * Method:    compressBound
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_luben_zstd_Zstd_compressBound
  (JNIEnv *, jclass, jlong);

/*
 * Class:     com_github_luben_zstd_Zstd
 * Method:    isError
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_luben_zstd_Zstd_isError
  (JNIEnv *, jclass, jlong);

/*
 * Class:     com_github_luben_zstd_Zstd
 * Method:    getErrorName
 * Signature: (J)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_github_luben_zstd_Zstd_getErrorName
  (JNIEnv *, jclass, jlong);

/*
 * Class:     com_github_luben_zstd_Zstd
 * Method:    trainFromBuffer
 * Signature: ([[B[BZ)J
 */
JNIEXPORT jlong JNICALL Java_com_github_luben_zstd_Zstd_trainFromBuffer
  (JNIEnv *, jclass, jobjectArray, jbyteArray, jboolean);

/*
 * Class:     com_github_luben_zstd_Zstd
 * Method:    trainFromBufferDirect
 * Signature: (Ljava/nio/ByteBuffer;[ILjava/nio/ByteBuffer;Z)J
 */
JNIEXPORT jlong JNICALL Java_com_github_luben_zstd_Zstd_trainFromBufferDirect
  (JNIEnv *, jclass, jobject, jintArray, jobject, jboolean);

/*
 * Class:     com_github_luben_zstd_Zstd
 * Method:    getDictIdFromFrame
 * Signature: ([B)J
 */
JNIEXPORT jlong JNICALL Java_com_github_luben_zstd_Zstd_getDictIdFromFrame
  (JNIEnv *, jclass, jbyteArray);

/*
 * Class:     com_github_luben_zstd_Zstd
 * Method:    getDictIdFromFrameBuffer
 * Signature: (Ljava/nio/ByteBuffer;)J
 */
JNIEXPORT jlong JNICALL Java_com_github_luben_zstd_Zstd_getDictIdFromFrameBuffer
  (JNIEnv *, jclass, jobject);

/*
 * Class:     com_github_luben_zstd_Zstd
 * Method:    getDictIdFromDict
 * Signature: ([B)J
 */
JNIEXPORT jlong JNICALL Java_com_github_luben_zstd_Zstd_getDictIdFromDict
  (JNIEnv *, jclass, jbyteArray);

/*
 * Class:     com_github_luben_zstd_Zstd
 * Method:    magicNumber
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_github_luben_zstd_Zstd_magicNumber
  (JNIEnv *, jclass);

/*
 * Class:     com_github_luben_zstd_Zstd
 * Method:    windowLogMin
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_github_luben_zstd_Zstd_windowLogMin
  (JNIEnv *, jclass);

/*
 * Class:     com_github_luben_zstd_Zstd
 * Method:    windowLogMax
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_github_luben_zstd_Zstd_windowLogMax
  (JNIEnv *, jclass);

/*
 * Class:     com_github_luben_zstd_Zstd
 * Method:    chainLogMin
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_github_luben_zstd_Zstd_chainLogMin
  (JNIEnv *, jclass);

/*
 * Class:     com_github_luben_zstd_Zstd
 * Method:    chainLogMax
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_github_luben_zstd_Zstd_chainLogMax
  (JNIEnv *, jclass);

/*
 * Class:     com_github_luben_zstd_Zstd
 * Method:    hashLogMin
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_github_luben_zstd_Zstd_hashLogMin
  (JNIEnv *, jclass);

/*
 * Class:     com_github_luben_zstd_Zstd
 * Method:    hashLogMax
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_github_luben_zstd_Zstd_hashLogMax
  (JNIEnv *, jclass);

/*
 * Class:     com_github_luben_zstd_Zstd
 * Method:    searchLogMin
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_github_luben_zstd_Zstd_searchLogMin
  (JNIEnv *, jclass);

/*
 * Class:     com_github_luben_zstd_Zstd
 * Method:    searchLogMax
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_github_luben_zstd_Zstd_searchLogMax
  (JNIEnv *, jclass);

/*
 * Class:     com_github_luben_zstd_Zstd
 * Method:    searchLengthMin
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_github_luben_zstd_Zstd_searchLengthMin
  (JNIEnv *, jclass);

/*
 * Class:     com_github_luben_zstd_Zstd
 * Method:    searchLengthMax
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_github_luben_zstd_Zstd_searchLengthMax
  (JNIEnv *, jclass);

/*
 * Class:     com_github_luben_zstd_Zstd
 * Method:    frameHeaderSizeMin
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_github_luben_zstd_Zstd_frameHeaderSizeMin
  (JNIEnv *, jclass);

/*
 * Class:     com_github_luben_zstd_Zstd
 * Method:    frameHeaderSizeMax
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_github_luben_zstd_Zstd_frameHeaderSizeMax
  (JNIEnv *, jclass);

/*
 * Class:     com_github_luben_zstd_Zstd
 * Method:    blockSizeMax
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_github_luben_zstd_Zstd_blockSizeMax
  (JNIEnv *, jclass);

/*
 * Class:     com_github_luben_zstd_Zstd
 * Method:    minCompressionLevel
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_github_luben_zstd_Zstd_minCompressionLevel
  (JNIEnv *, jclass);

/*
 * Class:     com_github_luben_zstd_Zstd
 * Method:    maxCompressionLevel
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_github_luben_zstd_Zstd_maxCompressionLevel
  (JNIEnv *, jclass);

#ifdef __cplusplus
}
#endif
#endif
