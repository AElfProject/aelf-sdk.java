package io.aelf.schemas;

/**
 * @author linhui linhui@tydic.com
 * @title: MinerInRoundDto
 * @description: TODO
 * @date 2019/12/1514:33
 */
public class MinerInRoundDto {
    private int Order;
    private int ProducedTinyBlocks;
    private String ExpectedMiningTime;
    private String ActualMiningTimes;
    private String InValue;
    private String PreviousInValue;
    private String OutValue;
    private long ProducedBlocks;
    private long MissedBlocks;
    private long ImpliedIrreversibleBlockHeight;
}
