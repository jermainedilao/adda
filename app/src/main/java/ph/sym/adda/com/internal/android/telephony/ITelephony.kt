package ph.sym.adda.com.internal.android.telephony


interface ITelephony {
    fun endCall(): Boolean

    fun answerRingingCall()

    fun silenceRinger()
}