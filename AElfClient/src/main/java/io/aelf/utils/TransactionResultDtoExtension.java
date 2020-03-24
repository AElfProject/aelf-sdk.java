package io.aelf.utils;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import io.aelf.protobuf.generated.TransactionFee.ResourceTokenCharged;
import io.aelf.protobuf.generated.TransactionFee.TransactionFeeCharged;
import io.aelf.schemas.LogEventDto;
import io.aelf.schemas.TransactionResultDto;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.codec.binary.Base64;

/**
 * @author linhui linhui@tydic.com
 * @title: TransactionResultDtoExtension
 * @description: TODO
 * @date 2020/3/230:40
 */
public class TransactionResultDtoExtension {

  public static HashMap<String,Long> getTransactionFees(TransactionResultDto transactionResultDto)
      throws InvalidProtocolBufferException {
    HashMap transactionFeesDict = new HashMap<String,Long>();
    if(transactionResultDto==null || transactionResultDto.getLogs()==null){
      return transactionFeesDict;
    }
    List<LogEventDto> eventLogs = transactionResultDto.getLogs();
    for (LogEventDto log:eventLogs)
    {
      if (StringUtil.toString(log.getName()).contains("TransactionFeeCharged"))
      {
        Base64 base64 = new Base64();
        byte[] byteStringMessage=base64.decode(log.getNonIndexed());
        TransactionFeeCharged info = TransactionFeeCharged.parseFrom(ByteString.copyFrom(byteStringMessage));
        transactionFeesDict.put(info.getSymbol(), info.getAmount());
      }
    }

    for (LogEventDto log:eventLogs)
    {
      if (StringUtil.toString(log.getName()).contains("ResourceTokenCharged"))
      {
        Base64 base64 = new Base64();
        byte[] byteStringMessage=base64.decode(log.getNonIndexed());
        ResourceTokenCharged info = ResourceTokenCharged.parseFrom(ByteString.copyFrom(byteStringMessage));
        transactionFeesDict.put(info.getSymbol(), info.getAmount());
      }
    }


    return transactionFeesDict;
  }
}
