package com.example.voucher.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.example.voucher.domain.Voucher;

public class MemoryVoucherRepository implements VoucherRepository {

	private final Map<UUID, Voucher> voucherMap = new ConcurrentHashMap<>();

	@Override
	public UUID save(Voucher voucher) {
		UUID voucherId = voucher.getVoucherId();
		voucherMap.put(voucherId, voucher);

		return voucherMap.get(voucherId).getVoucherId();
	}

	@Override
	public List<Voucher> findAll() {
		return new ArrayList<>(voucherMap.values());
	}

}
