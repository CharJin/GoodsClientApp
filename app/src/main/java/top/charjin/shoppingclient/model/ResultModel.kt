package top.charjin.shoppingclient.model

class ResultModel<T>() {
    var code: Int = 0
    lateinit var msg: String
    var data: T? = null

    constructor(code: Int, msg: String, data: T) : this() {
        this.code = code
        this.msg = msg
        this.data = data
    }
}