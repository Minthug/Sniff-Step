'use client'

import React from 'react'
import { container } from '@/app/common'
import { LocaleRegisterWalker, Locales } from '@/app/types/locales'
import { FileChange, RegisterWalker } from '@/app/hooks'
import { ChooseImageFile, ChooseWalkDates, ChooseWalkTimes, DescriptionModal, DescriptionTextarea } from '.'
import { AiOutlineSearch } from 'react-icons/ai'
import { styles } from '../resource'

interface Props {
    lang: Locales
    text: LocaleRegisterWalker
    fileChangeState: FileChange
    registerWalkerState: RegisterWalker
}

export function Mobile({ lang, fileChangeState, registerWalkerState }: Props) {
    const { file, fileSizeError, handleFileChange } = fileChangeState
    const {
        days,
        times,
        title,
        description,
        descriptionSizeError,
        descriptionExample,
        showDescriptionModal,
        handleDayChange,
        handleTimeChange,
        handleTitleChange,
        changeDayToKorean,
        changeTimeToKorean,
        handleDescriptionChange,
        setShowDescriptionModal
    } = registerWalkerState
    return (
        <div className={container.main.mobile}>
            <div className="mb-4 border-b">
                <input
                    type="text"
                    value={title}
                    className="w-full bg-[transparent] outline-none text-[20px] placeholder:text-[#d9d9d9]"
                    placeholder="ex) 문래동 댕댕이 산책 시켜드립니다 👍"
                    onChange={handleTitleChange}
                />
            </div>
            <div>
                <div className="mb-8">
                    <div className={styles.subject.mobile}>1. 본인을 표현할 수 있는 사진을 올려보세요!</div>
                    <ChooseImageFile file={file} handleFileChange={handleFileChange} />
                    {fileSizeError && (
                        <div className="text-[12px] text-[#ff0000]">파일 크기가 너무 큽니다. 100MB 이하로 업로드해주세요.</div>
                    )}
                </div>
                <div className="mb-8">
                    <div className={styles.subject.mobile}>2. 활동하실 주소를 정해주세요!</div>
                    <button className="relative w-full max-w-[480px] h-[40px] pr-8 text-[16px] text-start border border-gray-300 rounded-md overflow-hidden text-ellipsis whitespace-nowrap bg-white">
                        <AiOutlineSearch className="absolute top-1/2 right-[4px] translate-y-[-50%] text-gray-400 text-[24px]" />
                    </button>
                </div>
                <div className="mb-8">
                    <div className={styles.subject.mobile}>3. 활동하실 요일을 정해주세요!</div>
                    <ChooseWalkDates days={days} lang={lang} handleDayChange={handleDayChange} changeDayToKorean={changeDayToKorean} />
                </div>
                <div className="mb-8">
                    <div className={styles.subject.mobile}>4. 산책이 가능한 시간대를 골라주세요!</div>
                    <ChooseWalkTimes
                        times={times}
                        lang={lang}
                        handleTimeChange={handleTimeChange}
                        changeTimeToKorean={changeTimeToKorean}
                    />
                </div>
                <div className="mb-8">
                    <div className={`${styles.subject.mobile} flex flex-col justify-between gap-2`}>
                        <div className="mb-2">5. 견주님들께 산책에 대한 경험 및 자세한 플랜을 설명해보세요!</div>
                        <button
                            onClick={() => setShowDescriptionModal(!showDescriptionModal)}
                            className="px-4 py-4 border rounded-sm bg-white text-[14px]"
                        >
                            예시 템플릿 가져오기
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
                    className={`
                        w-full h-[60px] bg-green-900  text-[#fff] rounded-md
                        active:bg-green-800 mb-8
                    `}
                >
                    게시글 등록하기
                </button>
            </div>
        </div>
    )
}
