package com.example.voucher.domain;

import static com.example.voucher.constant.ExceptionMessage.*;
import java.util.Objects;
import java.util.UUID;
import com.example.voucher.constant.VoucherType;

public class FixedAmountVoucher implements Voucher {

    private final VoucherType voucherType = VoucherType.FIXED_AMOUNT_DISCOUNT;

    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(long amount) {
        validatePositive(amount);
        this.voucherId = UUID.randomUUID();
        this.amount = amount;
    }

    protected FixedAmountVoucher(UUID voucherId, Long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public Long getValue() {
        return amount;
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }

    @Override
    public long discount(long originalAmount) {
        validatePositive(originalAmount);
        validateGreaterThan(originalAmount, amount);

        return originalAmount - amount;
    }

    private void validateGreaterThan(long value, long threshold) {
        if (value <= threshold) {
            throw new IllegalArgumentException(
                String.format("{} {}", FORMAT_ERROR_GREATER_THAN_CONSTRAINT, threshold));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        FixedAmountVoucher that = (FixedAmountVoucher)o;
        return voucherId.equals(that.voucherId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucherId);
    }

}
