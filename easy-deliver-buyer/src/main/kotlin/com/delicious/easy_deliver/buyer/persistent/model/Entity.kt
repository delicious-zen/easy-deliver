package com.delicious.easy_deliver.buyer.persistent.model

import com.delicious.easy_deliver.buyer.annotation.NoArg
import kotlinx.serialization.Serializable
import java.time.LocalDate
import javax.persistence.*

@Entity
@NoArg
data class Buyer(
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long?,
        var name: String?,
        var gender: Int?,
        var phone: String?,
        var email: String?,
        @Transient
        var addresses:List<Address>
)


@Entity
@Table(indexes =
[
  Index(columnList = "buyer_id")
])
@NoArg
data class Address(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long?,
        var value: String?,
        @Column(name = "buyer_id")
        var buyerId: Long?
)

@Entity
@Table(
        indexes = [
          Index(columnList = "buyer_id"),
          Index(columnList = "deliveryman_id")
        ]
)
@NoArg
data class DeliveryOrder(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long?,

        @Column(name = "buyer_id")
        var buyerId: Long?,
        @Column(name = "deliveryman_id")
        var deliverymanId: Long?,

        var buyerPhone: String?,
        var address: String?,
        var expireTime: LocalDate?,
        /**
         * 订单状态
         *
         * 1 创建
         * 2 已支付
         * 3 取消
         * 4 完成
         */
        var status: Int?,

        @Transient
        var buyer: Buyer?,
        @Transient
        var deliveryman: Deliveryman?
)

@Entity
@NoArg
data class Deliveryman(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long?,
        var name: String?,
        var gender: Int?,
        var phone: String?,
        var email: String?,
        var grade: Double?,
        var description: String?,

        @Transient
        var list: List<DeliveryOrder>?
)
