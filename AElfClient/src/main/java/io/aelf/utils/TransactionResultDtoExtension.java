package io.aelf.utils;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
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

  public static HashMap<String,Long> GetTransactionFees(TransactionResultDto transactionResultDto)
      throws InvalidProtocolBufferException {
    HashMap transactionFeesDict = new HashMap<String,Long>();
    List<LogEventDto> eventLogs = transactionResultDto.getLogs();
    if (eventLogs == null) return transactionFeesDict;
    for (LogEventDto log:eventLogs)
    {
      if (StringUtil.toString(log.getName()).contains("ResourceTokenCharged") || StringUtil.toString(log.getName()).contains("TransactionFeeCharged"))
      {
        Base64 base64 = new Base64();
        byte[] byteStringMessage=base64.decode(log.getNonIndexed());
        TransactionFeeCharged info = TransactionFeeCharged.parseFrom(ByteString.copyFrom(byteStringMessage));
        transactionFeesDict.put(info.getSymbol(), info.getAmount());
      }
    }

    return transactionFeesDict;
  }
}
