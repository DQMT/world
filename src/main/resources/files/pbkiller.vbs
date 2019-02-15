Function CurrProcessId
    Dim oShell, sCmd, oWMI, oChldPrcs, oCols, lOut
    lOut = 0
    Set oShell  = CreateObject("WScript.Shell")
    Set oWMI    = GetObject(_
        "winmgmts:{impersonationLevel=impersonate}!\\.\root\cimv2")
    sCmd = "/K " & Left(CreateObject("Scriptlet.TypeLib").Guid, 38)
    oShell.Run "%comspec% " & sCmd, 0

    rem WScript.Sleep 100 'For healthier skin, get some sleep
    Set oChldPrcs = oWMI.ExecQuery("Select * From Win32_Process Where CommandLine Like '%" & sCmd & "'",,32)
    For Each oCols In oChldPrcs
        lOut = oCols.ParentProcessId
        oCols.Terminate
        Exit For
    Next
    CurrProcessId = lOut
End Function

Function KillProc(pid)
    dim WSHshellA
    set WSHshellA = wscript.createobject("wscript.shell")
    WSHshellA.run "cmd.exe /c taskkill /f /pid "+pid
End Function

Dim WshShell,pid,newVBS
Set fso = CreateObject("Scripting.FileSystemObject")
fileName = split(WScript.ScriptName,"_")
If ubound(fileName) > 0 Then
pid = Replace(fileName(1),".vbs","")
MsgBox "close: pid="+ pid
KillProc(pid)
newVBS = fileName(0) + ".vbs"
fso.MoveFile WScript.ScriptFullName, newVBS
Else
MsgBox "open: pid="+cstr(CurrProcessId)
newVBS = Replace(WScript.ScriptFullName,"pbkiller","pbkiller_" + cstr(CurrProcessId))
fso.MoveFile WScript.ScriptFullName, newVBS
Set WshShell=WScript.CreateObject("WScript.Shell")
While true
WScript.Sleep 30000
WshShell.SendKeys "{NUMLOCK}"
WshShell.SendKeys "{NUMLOCK}"
Wend
End If


