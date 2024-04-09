import React from 'react'
import { D2CodingBold } from '@/app/fonts'
import { SigninButton, SigninInput, SigninLogo, container } from '@/app/common'
import { Locales } from '@/app/types/locales'
import { SignupStates } from '@/app/hooks/useSignup'
import { useRouter } from 'next/navigation'

interface Props {
    lang: Locales
    signupStates: SignupStates
}
export function Mobile({ lang, signupStates }: Props) {
    const router = useRouter()
    const {
        name,
        nickName,
        email,
        password,
        phoneNumber,
        changeName,
        changeNickname,
        changeEmail,
        changePassword,
        changePhoneNumber,
        changeIsAgreed
    } = signupStates
    return (
        <div className={container.autentication.mobile.section}>
            <div className={container.autentication.mobile.main}>
                <SigninLogo lang={lang} />
                <div className="mb-4">
                    <div className={`${D2CodingBold.className} mb-2`}>Name</div>
                    <SigninInput value={name} placeholder="Enter your name" type="text" onChange={changeName} />
                </div>
                <div className="mb-4">
                    <div className={`${D2CodingBold.className} mb-2`}>Nickname</div>
                    <SigninInput value={nickName} placeholder="Enter your nickname" type="text" onChange={changeNickname} />
                </div>
                <div className="mb-4">
                    <div className={`${D2CodingBold.className} mb-2`}>Email</div>
                    <SigninInput value={email} placeholder="Enter your email" type="text" onChange={changeEmail} />
                </div>
                <div className="mb-4">
                    <div className={`${D2CodingBold.className} mb-2`}>Password</div>
                    <SigninInput
                        value={password}
                        placeholder="Use 6+ characters, mixing letters and numbers."
                        type="password"
                        onChange={changePassword}
                    />
                </div>
                <div className="mb-4">
                    <div className={`${D2CodingBold.className} mb-2`}>Phone Number</div>
                    <SigninInput value={phoneNumber} placeholder="01012341234" type="text" onChange={changePhoneNumber} />
                </div>
                <div className="flex items-center gap-4 mb-8">
                    <input onChange={changeIsAgreed} className="w-[20px] h-[20px]" type="checkbox" />
                    <div className="flex flex-wrap text-[12px]">
                        <div>I agreed with Sniff & Steps&nbsp;</div>
                        <button className="underline select-none">Terms of Service</button>,&nbsp;
                        <button className="underline select-none">Privacy Policy</button>
                    </div>
                </div>
                <SigninButton theme="dark" onClick={() => {}} className="active:bg-gray-800">
                    Sign up
                </SigninButton>
                <div className="flex justify-center items-center text-[12px]">
                    Already have an account?&nbsp;
                    <button className="underline select-none active:bg-slate-100" onClick={() => router.push(`/${lang}/signin`)}>
                        Sign In
                    </button>
                </div>
            </div>
        </div>
    )
}
