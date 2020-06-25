package com.delicious.easy_deliver.buyer.persistent.repository

import com.delicious.easy_deliver.buyer.persistent.model.Buyer
import org.springframework.data.jpa.repository.JpaRepository

interface BuyerRepository: JpaRepository<Buyer,Long>

