package com.example.voucher.wallet.model;

import java.util.UUID;

import com.example.voucher.query.marker.Entity;

public class Wallet implements Entity {

    private final UUID walletId;
    private final UUID customerId;
    private final UUID voucherId;

    public Wallet(UUID customerId, UUID voucherId) {
        this.walletId = UUID.randomUUID();
        this.customerId = customerId;
        this.voucherId = voucherId;
    }

    public Wallet(UUID walletId, UUID customerId, UUID voucherId) {
        this.walletId = walletId;
        this.customerId = customerId;
        this.voucherId = voucherId;
    }

    public UUID getWalletId() {
        return walletId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public UUID getVoucherId() {
        return voucherId;
    }
}
