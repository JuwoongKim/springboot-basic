package com.example.voucher.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.voucher.domain.FixedAmountVoucher;
import com.example.voucher.domain.PercentDiscountVoucher;
import com.example.voucher.domain.Voucher;

import com.example.voucher.repository.VoucherRepository;
import com.example.voucher.utils.Validator;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createFixedAmountDiscountVoucher(long discountAmount) {
        Validator.validatePositive(discountAmount);

        Voucher voucher = new FixedAmountVoucher(discountAmount);
        voucherRepository.save(voucher);

        return voucher;

    }

    public Voucher createPercentDiscountVoucher(long discountPercent) {
        Validator.validatePercent(discountPercent);

        Voucher voucher = new PercentDiscountVoucher(discountPercent);
        voucherRepository.save(voucher);

        return voucher;

    }

    public List<Voucher> getVouchers() {
        return voucherRepository.findAll();

    }

}
