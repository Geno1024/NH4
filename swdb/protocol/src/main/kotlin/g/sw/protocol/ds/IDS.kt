package g.sw.protocol.ds

import g.sw.protocol.box.IB

interface IDS
{
    fun toMap(): Map<String, IB<*>> = javaClass.declaredFields.associate { field ->
        field.name to field.apply { isAccessible = true }.get(this) as IB<*>
    }

    companion object
    {
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
