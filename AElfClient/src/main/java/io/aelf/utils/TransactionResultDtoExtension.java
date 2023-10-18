package io.aelf.utils;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

import io.aelf.enums.TransferredEnum;
import io.aelf.protobuf.generated.TokenContract;
import io.aelf.protobuf.generated.TransactionFee.ResourceTokenCharged;
import io.aelf.protobuf.generated.TransactionFee.TransactionFeeCharged;
import io.aelf.schemas.LogEventDto;
import io.aelf.schemas.TransactionResultDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.codec.binary.Base64;

/**
 * @author linhui linhui@tydic.com
 * @title: TransactionResultDtoExtension
 * @description: TODO
 * @date 2020/3/230:40
 */
public class TransactionResultDtoExtension {

    public static HashMap<String, Long> getTransactionFees(TransactionResultDto transactionResultDto) throws InvalidProtocolBufferException {
        HashMap transactionFeesDict = new HashMap<String, Long>();
        if (transactionResultDto == null || transactionResultDto.getLogs() == null) {
            return transactionFeesDict;
        }
        List<LogEventDto> eventLogs = transactionResultDto.getLogs();
        for (LogEventDto log : eventLogs) {
            if (StringUtil.toString(log.getName()).contains("TransactionFeeCharged")) {
                Base64 base64 = new Base64();
                byte[] byteStringMessage = base64.decode(log.getNonIndexed());
                TransactionFeeCharged info = TransactionFeeCharged.parseFrom(ByteString.copyFrom(byteStringMessage));
                transactionFeesDict.put(info.getSymbol(), info.getAmount());
            }
        }

        for (LogEventDto log : eventLogs) {
            if (StringUtil.toString(log.getName()).contains("ResourceTokenCharged")) {
                Base64 base64 = new Base64();
                byte[] byteStringMessage = base64.decode(log.getNonIndexed());
                ResourceTokenCharged info = ResourceTokenCharged.parseFrom(ByteString.copyFrom(byteStringMessage));
                transactionFeesDict.put(info.getSymbol(), info.getAmount());
            }
        }


        return transactionFeesDict;
    }


    public static List<TokenContract.Transferred> getTransferredEvent(String contractAddress, TransactionResultDto transactionResultDto) throws InvalidProtocolBufferException {
        if (transactionResultDto == null || transactionResultDto.getLogs() == null) {
            return null;
        }
        List<LogEventDto> logEventDtos = getLogsEventDto(TransferredEnum.TRANSFERRED, transactionResultDto, contractAddress);
        List<TokenContract.Transferred> transferreds = new ArrayList<>();
        for (LogEventDto log : logEventDtos) {

            TokenContract.Transferred.Builder result = TokenContract.Transferred.getDefaultInstance().toBuilder();
            byte[] indexedBytes = Base64.decodeBase64(log.getIndexed().get(0));
            result.setFrom(TokenContract.Transferred.getDefaultInstance().parseFrom(indexedBytes).getFrom());

            indexedBytes = Base64.decodeBase64(log.getIndexed().get(1));
            result.setTo(TokenContract.Transferred.getDefaultInstance().parseFrom(indexedBytes).getTo());

            indexedBytes = Base64.decodeBase64(log.getIndexed().get(2));
            result.setSymbol(TokenContract.Transferred.getDefaultInstance().parseFrom(indexedBytes).getSymbol());

            byte[] nonIndexedBytes = Base64.decodeBase64(log.getNonIndexed());
            TokenContract.Transferred nonIndexed = TokenContract.Transferred.getDefaultInstance().parseFrom(nonIndexedBytes);
            result.setAmount(nonIndexed.getAmount()).setMemo(nonIndexed.getMemo());
            transferreds.add(result.build());
        }

        return transferreds;
    }

    public static List<TokenContract.CrossChainReceived> getCrossChainReceivedEvent(String contractAddress, TransactionResultDto transactionResultDto) throws InvalidProtocolBufferException {
        if (transactionResultDto == null || transactionResultDto.getLogs() == null) {
            return null;
        }
        List<LogEventDto> logEventDtos = getLogsEventDto(TransferredEnum.CROSSCHAINRECEIVED, transactionResultDto, contractAddress);
        List<TokenContract.CrossChainReceived> crossChainReceiveds = new ArrayList<>();

        for (LogEventDto log : logEventDtos) {
            TokenContract.CrossChainReceived.Builder builder = TokenContract.CrossChainReceived.getDefaultInstance().toBuilder();
            byte[] nonIndexedBytes = Base64.decodeBase64(log.getNonIndexed());
            TokenContract.CrossChainReceived nonIndexed = TokenContract.CrossChainReceived.getDefaultInstance().parseFrom(nonIndexedBytes);
            builder.setAmount(nonIndexed.getAmount())
                    .setMemo(nonIndexed.getMemo())
                    .setFrom(nonIndexed.getFrom())
                    .setTo(nonIndexed.getTo())
                    .setSymbol(nonIndexed.getSymbol())
                    .setIssueChainId(nonIndexed.getIssueChainId())
                    .setFromChainId(nonIndexed.getFromChainId())
                    .setParentChainHeight(nonIndexed.getParentChainHeight())
                    .setTransferTransactionId(nonIndexed.getTransferTransactionId());
            crossChainReceiveds.add(builder.build());
        }
        return crossChainReceiveds;
    }

    public static List<TokenContract.CrossChainTransferred> getCrossChainTransferredEvent(String contractAddress, TransactionResultDto transactionResultDto) throws InvalidProtocolBufferException {
        if (transactionResultDto == null || transactionResultDto.getLogs() == null) {
            return null;
        }
        List<LogEventDto> logEventDtos = getLogsEventDto(TransferredEnum.CROSSCHAINTRANSFERRED, transactionResultDto, contractAddress);
        List<TokenContract.CrossChainTransferred> crossChainTransferreds = new ArrayList<>();

        for (LogEventDto log : logEventDtos) {
            TokenContract.CrossChainTransferred.Builder builder = TokenContract.CrossChainTransferred.getDefaultInstance().toBuilder();
            byte[] nonIndexedBytes = Base64.decodeBase64(log.getNonIndexed());
            TokenContract.CrossChainTransferred nonIndexed = TokenContract.CrossChainTransferred.getDefaultInstance().parseFrom(nonIndexedBytes);
            builder.setAmount(nonIndexed.getAmount())
                    .setMemo(nonIndexed.getMemo())
                    .setFrom(nonIndexed.getFrom())
                    .setTo(nonIndexed.getTo())
                    .setSymbol(nonIndexed.getSymbol())
                    .setIssueChainId(nonIndexed.getIssueChainId())
                    .setToChainId(nonIndexed.getToChainId());
            crossChainTransferreds.add(builder.build());
        }
        return crossChainTransferreds;
    }


    public static List<LogEventDto> getLogsEventDto(TransferredEnum transferredEnum, TransactionResultDto resultDto, String contractAddress) {
        List<LogEventDto> eventLogs = resultDto.getLogs();
        Map<String, List<LogEventDto>> logMap = eventLogs.stream().collect(Collectors.groupingBy(LogEventDto::getAddress));
        List<LogEventDto> logEventDtos = logMap.get(contractAddress);
        logEventDtos = logEventDtos.stream().filter(logEventDto -> logEventDto.getName().equals(transferredEnum.getDescription())).collect(Collectors.toList());
        return logEventDtos;
    }


}
