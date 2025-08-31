package g.sw.protocol.ds

interface IO
{
    fun toMap(): Map<String, Any> = javaClass.declaredFields.associate { field ->
        field.name to field.apply { isAccessible = true }.get(this)
    }

    companion object
    {
        inline fun <reified T : IO> fromMap(value: Map<String, Any>): T = with(T::class.constructors.first {
            it.name == "<init>"
        }) {
            call(
                *(parameters.map { param ->
                    value[param.name]
                }.toTypedArray())
            )
        }
    }
}
