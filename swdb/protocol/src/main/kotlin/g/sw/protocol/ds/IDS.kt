package g.sw.protocol.ds

import g.sw.protocol.box.IB
import kotlin.reflect.KClass

interface IDS
{
    fun toMap(): Map<String, IB<*>> = javaClass.declaredFields
        .filterNot { it.name == "Companion" }
        .associate { field ->
            field.name to field.apply { isAccessible = true }.get(this) as IB<*>
        }

    companion object
    {
        fun <T : IDS> fromMap(clazz: KClass<T>, value: Map<String, IB<*>>): T = with (clazz.constructors.first {
            it.name == "<init>"
        }) {
            call(
                *(parameters.map { param ->
                    value[param.name]
                }.toTypedArray())
            )
        }

        inline fun <reified T : IDS> fromMap(value: Map<String, IB<*>>): T = with(T::class.constructors.first {
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
