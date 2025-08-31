package g.sw.protocol.ds

import kotlin.reflect.KClass

interface IO
{
    fun toMap(): Map<String, Any> = javaClass.declaredFields.associate { field ->
        field.name to field.apply { isAccessible = true }.get(this)
    }

    companion object
    {
        fun fromMap(clazz: KClass<out IO>, value: Map<String, Any>): IO = with(clazz.constructors.first {
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
