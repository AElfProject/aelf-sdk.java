package io.aelf.utils;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import io.aelf.protobuf.generated.TransactionFee.ResourceTokenCharged;
import io.aelf.protobuf.generated.TransactionFee.TransactionFeeCharged;
import io.aelf.schemas.LogEventDto;
import io.aelf.schemas.TransactionResultDto;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;

public class TransactionResultDtoExtension {

  public static HashMap<String,Long> getTransactionFees(TransactionResultDto transactionResultDto)
      throws InvalidProtocolBufferException {
    HashMap<String,Long> transactionFeesDict = new HashMap<>();
    Base64.Decoder decoder=Base64.getDecoder();
    if(transactionResultDto==null || transactionResultDto.getLogs()==null){
      return transactionFeesDict;
    }
    List<LogEventDto> eventLogs = transactionResultDto.getLogs();
    for (LogEventDto log:eventLogs)
    {
      if (StringUtil.toString(log.getName()).contains("TransactionFeeCharged"))
      {
        byte[] byteStringMessage=decoder.decode(log.getNonIndexed());
        TransactionFeeCharged info = TransactionFeeCharged.parseFrom(ByteString.copyFrom(byteStringMessage));
        transactionFeesDict.put(info.getSymbol(), info.getAmount());
      }
    }

    for (LogEventDto log:eventLogs)
    {
      if (StringUtil.toString(log.getName()).contains("ResourceTokenCharged"))
      {
        byte[] byteStringMessage=decoder.decode(log.getNonIndexed());
        ResourceTokenCharged info = ResourceTokenCharged.parseFrom(ByteString.copyFrom(byteStringMessage));
        transactionFeesDict.put(info.getSymbol(), info.getAmount());
      }
    }


    return transactionFeesDict;
  }
}
