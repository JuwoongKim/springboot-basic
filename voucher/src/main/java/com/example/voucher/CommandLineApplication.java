package com.example.voucher;

import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.example.voucher.constant.ConstantStrings;
import com.example.voucher.domain.Voucher;
import com.example.voucher.domain.dto.VoucherDTO;
import com.example.voucher.domain.enums.VoucherType;
import com.example.voucher.io.Console;
import com.example.voucher.io.ModeType;
import com.example.voucher.service.VoucherService;

import jakarta.annotation.PostConstruct;

@Controller
public class CommandLineApplication {

	private static final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);

	final private VoucherService voucherService;
	private boolean isOn = true;

	public CommandLineApplication(VoucherService voucherService) {
		this.voucherService = voucherService;
	}

	@PostConstruct
	public void init() {
		run();
	}

	public void run() {
		while (isOn) {
			Console.printModeType();
			String readModeType = Console.readModeType();

			try{
				ModeType modeType = ModeType.getTypeMode(readModeType);
				processMode(modeType);
			}catch(NoSuchElementException e){
				logger.error(ConstantStrings.PREFIX_NO_SUCH_ELEMENT_EXCEPTION_MESSAGE + e.getMessage());
				Console.printError(ConstantStrings.MESSAGE_PRINT_RETRY_MODE_SELECTION_PROMPT);
			}
		}
	}

	public void processMode(ModeType mode) {
		switch (mode) {
			case Exit -> isOn = false;
			case Create -> createVoucher();
			case List -> getVouchers();
		}
	}

	public void createVoucher() {
		try {
			createVoucherDetail();
		} catch (IllegalArgumentException e) {
			logger.error(ConstantStrings.PREFIX_ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE + e.getMessage());
			Console.printError(e.getMessage());
		} catch (InputMismatchException e) {
			logger.error(ConstantStrings.PREFIX_INPUT_MISMATCH_EXCEPTION_MESSAGE + e.getMessage());
			Console.printError(e.getMessage());
		} catch (NoSuchElementException e) {
			logger.error(ConstantStrings.PREFIX_NO_SUCH_ELEMENT_EXCEPTION_MESSAGE + e.getMessage());
			Console.printError(e.getMessage());
		} catch (Exception e) {
			logger.error(ConstantStrings.PREFIX_EXCEPTION_MESSAGE + e.getMessage());
			Console.printError(e.getMessage());
		}
	}

	public void createVoucherDetail() {
		Console.printVoucherType();
		Integer inputVoucherType = Console.readVoucherType();
		VoucherType voucherType = VoucherType.getVouchersType(inputVoucherType);
		processVoucherType(voucherType);
	}

	public Voucher processVoucherType(VoucherType voucherType) {
		Voucher voucher = switch (voucherType) {
			case FixedAmountDiscount -> {
				Console.printDiscountAmount();
				long discountAmount = Console.readDiscount();
				yield voucherService.createVoucher(voucherType, discountAmount);
			}
			case PercentDiscount -> {
				Console.printDiscountPercent();
				long discountPercent = Console.readDiscount();
				yield voucherService.createVoucher(voucherType, discountPercent);
			}
		};

		return voucher;
	}

	public void getVouchers() {
		List<Voucher> vouchers = voucherService.getVouchers();

		vouchers.stream()
			.map(o -> new VoucherDTO(o.getValue(), o.getVoucherType()))
			.forEach(Console::printVoucherInfo);
	}

}
