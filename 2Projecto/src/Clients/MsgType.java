package Clients;

/**
 * Classe que define um enumerado constituido por todos as mensagens dos estados possiveis do
 * programa.
 * @author Miguel Maia
 */
public enum MsgType {

    PLACEABET,
    GETBETS,
    HONOURTHEBETS,
    GOCOLLECTTHEGAINS,
    CALCULATETOTALBETVALUE,
    GETTOTALBETVALUE,
    PROCEEDTOSTARTLINE,
    HASFINISHLINEBEENCROSSED,
    STARTTHERACE,
    MAKEAMOVE,
    GETHORSEAGILITY,
    SETHORSEAGILITY,
    GETNSPECTATORS,
    SETNSPECTATORS,
    GETNHORSES,
    SETNHORSES,
    GETNRACES,
    SETNRACES,
    GETDISTANCIA,
    SETDISTANCIA,
    GETHORSEWINNERID,
    SETHORSEWINNERID,
    GETNWINNERS,
    SETNWINNERS,
    GETCURRENTRACE,
    SETCURRENTRACE,
    GETBETSPERPUNTER,
    SETBETSPERPUNTER,
    GETBETBYPUNTERID,
    RESETBETSPERID,
    GETHORSESPOSITIONS,
    SETHORSESPOSITIONS,
    GETRACEFINISHED,
    SETRACEFINISHED,
    WAITFORNEXTRACE,
    CALLPUNTERS,
    WATCHTHERACE,
    REPORTRESULTS,
    HAVEIWON,
    PROCEEDTOPADDOCK,
    GOCHECKHORSES,
    PROCEEDTOSTABLE,
    CALLTOPADDOCK,
    CLOSE, LOG,
    SUMMONHORSESTOPADDOCK,
    RELAXABIT,
    ACCEPTTHEBETS,
    ARETHEREANYWINNERS,
    ENTERTAINTHEGUESTS;
}
