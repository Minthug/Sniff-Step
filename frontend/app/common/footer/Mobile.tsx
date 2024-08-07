import React from 'react'
import { Button } from '@/app/common'
import { LocaleFooter } from '@/app/types/locales'

interface Props {
    lang: string
    text: LocaleFooter
}

export function Mobile({ lang, text }: Props) {
    return (
        <footer
            className={`
                xl:hidden 
                relative h-full min-h-[203px] flex flex-col justify-between px-4 py-8
            `}
        >
            <div className="flex justify-between">
                <div className="w-full flex flex-col">
                    <img className="w-[96px]" src="/images/text-logo-fit.png" alt="" />
                    <div className="flex flex-wrap gap-8 gap-y-0">
                        <Button className="text-sm" contents={text.home} href={`/${lang}`} />
                        <Button className="text-sm" contents={text.post} href={`/${lang}/boards/post`} />
                        <Button className="text-sm" contents={text.boards} href={`/${lang}/boards`} />
                        <Button className="text-sm" contents={text.login} href={`/${lang}/signin`} />
                        <Button className="text-sm" contents={text.language} href={`/${lang === 'en' ? 'ko' : 'en'}`} />
                    </div>
                </div>
            </div>
            <div className="flex flex-wrap gap-4 justify-between text-[12px] text-gray-400">
                <div className="flex items-center gap-4">
                    <div>@2024 Sniff & Step</div>
                    <div>Terms</div>
                    <div>Privacy</div>
                </div>
                <div className="flex items-center gap-4">
                    <div>@Minthug</div>
                    <div>@VVSOGI</div>
                </div>
            </div>
        </footer>
    )
}
