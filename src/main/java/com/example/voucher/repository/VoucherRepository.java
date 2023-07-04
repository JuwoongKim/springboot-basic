package com.example.voucher.repository;

import java.util.List;
import java.util.UUID;

import com.example.voucher.domain.Voucher;

public interface VoucherRepository {

    UUID save(Voucher voucher);

    List<Voucher> findAll();

}
