package io.aelf.schemas;

/**
 * @author linhui linhui@tydic.com
 * @title: RoundDto
 * @description: TODO
 * @date 2019/12/1514:32
 */
public class RoundDto {
    private long RoundNumber;
    private long TermNumber;
    private long RoundId;
    private MinerInRoundDto ExtraBlockProducerOfPreviousRound;
    private long ConfirmedIrreversibleBlockRoundNumber;
    private long ConfirmedIrreversibleBlockHeight;
    private boolean IsMinerListJustChanged;
}
