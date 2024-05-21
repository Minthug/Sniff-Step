'use client'

import React, { useState } from 'react'
import { container } from '@/app/common'
import { LocalePostBoard, Locales } from '@/app/types/locales'
import { FileChange, BoardState } from '@/app/hooks'
import { ChooseImageFile, ChooseWalkDates, ChooseWalkTimes, DescriptionModal, DescriptionTextarea, FindAddress } from '.'

interface Props {
    lang: Locales
    text: LocalePostBoard
    fileChangeState: FileChange
    boardState: BoardState
}

export function Desktop({ lang, text, fileChangeState, boardState }: Props) {
    const [isSubmitting, setIsSubmitting] = useState(false)
    const { file, fileSizeError, handleFileChange } = fileChangeState
    const {
        days,
        times,
        title,
        description,
        address,
        addressEnglish,
        descriptionExample,
        showDescriptionModal,
        titleError,
        addressError,
        dateError,
        timeError,
        descriptionError,
        descriptionSizeError,
        handleDayChange,
        handleTimeChange,
        handleTitleChange,
        handleChangeAddress,
        changeDayToKorean,
        changeTimeToKorean,
        handleDescriptionChange,
        setShowDescriptionModal,
        handlePost
    } = boardState

    return (
        <div className={container.main.desktop}>
            <div className="mb-8 border-b">
                <input
                    type="text"
                    value={title}
                    onChange={handleTitleChange}
                    className="w-full bg-[transparent] outline-none text-[48px] placeholder:text-[#d9d9d9]"
                    placeholder={text.titlePlaceholder}
                />
            </div>
            <div>
                <div className="mb-8">
                    <div className="text-[18px] font-[500] mb-4">1. {text.paragraph1}</div>
                    <ChooseImageFile file={file} handleFileChange={handleFileChange} />
                    {fileSizeError && <div className="text-[12px] text-[#ff0000]">{text.fileSizeError}</div>}
                </div>
                <div id="address" className="mb-8">
                    <div className="text-[18px] font-[500] mb-4">2. {text.paragraph2}</div>
                    <FindAddress
                        lang={lang}
                        text={text}
                        address={address}
                        addressEnglish={addressEnglish}
                        handleChangeAddress={handleChangeAddress}
                    />
                </div>
                <div id="date" className="mb-8">
                    <div className="text-[18px] font-[500] mb-4">3. {text.paragraph3}</div>
                    <ChooseWalkDates days={days} lang={lang} handleDayChange={handleDayChange} changeDayToKorean={changeDayToKorean} />
                </div>
                <div id="time" className="mb-8">
                    <div className="text-[18px] font-[500] mb-4">4. {text.paragraph4}</div>
                    <ChooseWalkTimes
                        times={times}
                        lang={lang}
                        handleTimeChange={handleTimeChange}
                        changeTimeToKorean={changeTimeToKorean}
                    />
                </div>
                <div id="description" className="mb-8">
                    <div className="flex items-center justify-between text-[18px] font-[500] mb-4">
                        <div>5. {text.paragraph5}</div>
                        <button
                            onClick={() => setShowDescriptionModal(!showDescriptionModal)}
                            className="px-4 py-2 border rounded-sm bg-white"
                        >
                            {text.templateButton}
                        </button>
                    </div>
                    <DescriptionTextarea
                        description={description}
                        descriptionExample={descriptionExample}
                        descriptionSizeError={descriptionSizeError}
                        handleDescriptionChange={handleDescriptionChange}
                    />
                </div>
            </div>
            {showDescriptionModal && (
                <DescriptionModal
                    descriptionExample={descriptionExample}
                    setShowDescriptionModal={setShowDescriptionModal}
                    handleDescriptionChange={handleDescriptionChange}
                />
            )}
            <div className="mb-8">
                <button
                    disabled={isSubmitting}
                    onClick={async () => {
                        setIsSubmitting(true)
                        try {
                            await handlePost(file)
                        } catch (error) {
                            setIsSubmitting(false)
                        }
                    }}
                    className={`
                        hover:bg-green-700
                        active:bg-green-800 mb-8
                        disabled:cursor-not-allowed disabled:bg-gray-500
                        w-full h-[60px] bg-green-900  text-[#fff] rounded-md
                    `}
                >
                    {text.post}
                </button>
                {titleError && <div className="text-[12px] text-[#ff0000]">{text.titleError}</div>}
                {addressError && <div className="text-[12px] text-[#ff0000]">{text.addressError}</div>}
                {dateError && <div className="text-[12px] text-[#ff0000]">{text.dateError}</div>}
                {timeError && <div className="text-[12px] text-[#ff0000]">{text.timeError}</div>}
                {descriptionError && <div className="text-[12px] text-[#ff0000]">{text.descriptionError}</div>}
            </div>
        </div>
    )
}
